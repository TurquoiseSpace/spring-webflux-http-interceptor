package open.source.exchange.service;

import java.util.Map;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.entity.InformationExchange;
import open.source.exchange.enumeration.ExchangeInformationType;
import open.source.exchange.enumeration.TimeEvent;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEnum;
import open.source.exchange.model.Time;
import open.source.exchange.parser.EnumParser;
import open.source.exchange.parser.ObjectParser;
import open.source.exchange.repository.asynchronous.InformationExchangeRepoAsync;
import open.source.exchange.utility.asynchronous.MonoCallSynchronousExecutor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

@Log4j2
@Service
@RequiredArgsConstructor
public class InformationExchangeService {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private EnumParser enumParser;

	@Autowired
	private ParserHelper parserHelper;

	@Autowired
	private InformationExchangeRepoAsync informationExchangeRepoAsync;

	// https://azizulhaq-ananto.medium.com/how-to-handle-logs-and-tracing-in-spring-webflux-and-microservices-a0b45adc4610
	// TODO : implement and add reference to InformationExchange
	// https://stackoverflow.com/questions/4967885/jvm-option-xss-what-does-it-do-exactly
	// https://stackoverflow.com/questions/6020619/where-can-i-find-default-xss-stack-size-value-for-sun-oracle-jvm

	public void onEntry(long startTimestamp, ServerWebExchange serverWebExchange, String identifier) {

		String requestId = serverWebExchange.getRequest().getId();
		log.trace("on entry -> (requestId) {} (startTimestamp) {} (identifier) {}",
				requestId, startTimestamp, identifier);

		ExBase exBase = objectParser.parse(serverWebExchange);
		InformationExchange informationExchange = new InformationExchange(exBase);
		informationExchange.setId(identifier);
		Mono<InformationExchange> monoCall = informationExchangeRepoAsync.insert(informationExchange)
			.flatMap(persistedInformationExchange -> {
				MDC.put("requestId", requestId);
				MDC.put("identifier", identifier);
				log.info("persisted -> (informationExchange) {}", persistedInformationExchange);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.attributes);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.logPrefix);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.notModifiedFlag);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.serverHttpRequest);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.principal);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.session);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.formData);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.multipartData);
				parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.localeContext);
				return Mono.just(persistedInformationExchange);
			});
		Thread helperThread = MonoCallSynchronousExecutor.waitForIt(monoCall, "server web exchange at entry", 50);
		try {
			helperThread.join();
		} catch (InterruptedException e) {
			log.error("interrupted", e);
		}
	}

	public void onExit(SignalType signalType, ServerWebExchange serverWebExchange, String identifier, Map<TimeEvent, Time<Long>> events) {

		log.trace("on exit -> (responseSignalType) {} (identifier) {}",
				signalType, identifier);

		ExEnum exSignalType = enumParser.parse(signalType);
		Mono<Boolean> signalTypeMonoCall = informationExchangeRepoAsync.updateAttribute(identifier, ExchangeInformationType.signalType.name(), exSignalType);
		Thread helperThread1 = MonoCallSynchronousExecutor.waitForIt(signalTypeMonoCall, "web exchange update " + ExchangeInformationType.signalType.name(), 50);

		Mono<Boolean> eventsMonoCall = informationExchangeRepoAsync.updateAttribute(identifier, ExchangeInformationType.events.name(), events);
		Thread helperThread2 = MonoCallSynchronousExecutor.waitForIt(eventsMonoCall, "web exchange update " + ExchangeInformationType.events.name(), 50);

		parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.serverHttpResponse);

		parserHelper.identifyAndPersist(serverWebExchange, ExchangeInformationType.applicationContext);
	}

}
