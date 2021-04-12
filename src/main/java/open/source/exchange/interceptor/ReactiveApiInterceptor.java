package open.source.exchange.interceptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.enumeration.TimeEvent;
import open.source.exchange.enumeration.TimeUnit;
import open.source.exchange.model.Time;
import open.source.exchange.service.InformationExchangeService;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class ReactiveApiInterceptor implements WebFilter {

	/**
	 * API Paths for which the mongo db entry should not be created
	 */
	private static final Set<String> EXCLUDE_API_PATHS = new HashSet<String>();

	@Autowired
	private InformationExchangeService informationExchangeService;

	// https://stackoverflow.com/questions/47091717/interceptor-in-spring-5-webflux
	// https://piotrminkowski.com/2019/10/15/reactive-logging-with-spring-webflux-and-logstash/
	// https://stackoverflow.com/questions/46154994/how-to-log-spring-5-webclient-call
	// https://stackoverflow.com/questions/5374455/what-does-java-option-xmx-stand-for

	private void pendingParsers() {

		// ClassParser -> ExClass
		// ChronologyParser -> ExChronology
		// ZonedDateTimeParser -> ExZonedDateTime
		// ServerHttpRequestParser -> ExServerHttpRequest
		// ServerHttpResponseParser -> ExServerHttpResponse
	}

	protected String identifier(String id, long startTime) {

		return id + "-" + startTime;
	}

	protected static void addApiPathToExcludeFromBeingPersisted(String apiPath) {

		EXCLUDE_API_PATHS.add(apiPath);
	}

	protected static void addApiPathsToExcludeFromBeingPersisted(Collection<String> apiPaths) {

		EXCLUDE_API_PATHS.addAll(apiPaths);
	}

	protected static Set<String> getPathsToExcludeFromBeingPersisted() {

		return EXCLUDE_API_PATHS.stream()
			.map(string -> new String(string))
			.collect(Collectors.toSet());
	}

	private Map<TimeEvent, Time<Long>> buildEvents(long startTimestamp, long endTimestamp, long totalTime) {

		Time<Long> begin = new Time<Long>(startTimestamp, TimeUnit.MilliSecond);
		Time<Long> end = new Time<Long>(endTimestamp, TimeUnit.MilliSecond);
		Time<Long> frame = new Time<Long>(totalTime, TimeUnit.NanoSecond);
		Map<TimeEvent, Time<Long>> events = new TreeMap<TimeEvent, Time<Long>>();
		events.put(TimeEvent.Begin, begin);
		events.put(TimeEvent.End, end);
		events.put(TimeEvent.Frame, frame);
		return events;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

		String requestId = serverWebExchange.getRequest().getId();
		MDC.put("requestId", requestId);

		// to get the unix timestamp or epoch seconds, the value of System.currentTimeMillis() should be divided by zero
		long startTimestamp = System.currentTimeMillis();
		// to get the nano timeframe precision computation, use value of System.nanoTime()
		long nanoFrameStart = System.nanoTime();

		String apiUriPath = serverWebExchange.getRequest().getPath().value();
		boolean persistFlag = !EXCLUDE_API_PATHS.contains(apiUriPath);

		log.info("request interceptor -> (requestId) {} (startTimestamp) {} (nanosFrameStart) {} (apiUriPath) {} (persistFlag) {}",
				requestId, startTimestamp, nanoFrameStart, apiUriPath, persistFlag);

		String identifier = identifier(requestId, startTimestamp);
		MDC.put("identifier", identifier);

		if (persistFlag) {
			informationExchangeService.onEntry(startTimestamp, serverWebExchange, identifier);
		}

		return webFilterChain.filter(serverWebExchange)
			.doFinally(signalType -> {

				long endTimestamp = System.currentTimeMillis();
				long nanoFrameEnd = System.nanoTime();
				long totalTime = nanoFrameEnd - nanoFrameStart;
				log.info("response interceptor -> (requestId) {} (endTimestamp) {} (nanoFrameEnd) {} (signalType) {} (totalTime) {} nano seconds",
						requestId, endTimestamp, nanoFrameEnd, signalType, totalTime);

				if (persistFlag) {
					Map<TimeEvent, Time<Long>> events = buildEvents(startTimestamp, endTimestamp, totalTime);
					informationExchangeService.onExit(signalType, serverWebExchange, identifier, events);
				}
			});
	}

}
