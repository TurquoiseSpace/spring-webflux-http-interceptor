package open.source.exchange.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.entity.ExApplicationContext;
import open.source.exchange.enumeration.ExchangeInformationType;
import open.source.exchange.model.ExLocaleContext;
import open.source.exchange.model.ExMultiValueMap;
import open.source.exchange.model.ExPart;
import open.source.exchange.model.ExPrincipal;
import open.source.exchange.model.ExServerHttpResponse;
import open.source.exchange.model.ExWebSession;
import open.source.exchange.parser.ApplicationContextParser;
import open.source.exchange.parser.LocaleContextParser;
import open.source.exchange.parser.MultiValueMapParser;
import open.source.exchange.parser.PartParser;
import open.source.exchange.parser.PrincipalParser;
import open.source.exchange.parser.ServerHttpRequestParser;
import open.source.exchange.parser.ServerHttpResponseParser;
import open.source.exchange.parser.WebSessionParser;
import open.source.exchange.repository.asynchronous.ExApplicationContextRepoAsync;
import open.source.exchange.repository.asynchronous.InformationExchangeRepoAsync;
import open.source.exchange.utility.asynchronous.MonoCallSynchronousExecutor;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class ParserHelper {

	@Autowired
	private ApplicationContextParser applicationContextParser;

	@Autowired
	private LocaleContextParser localeContextParser;

	@Autowired
	private ServerHttpRequestParser serverHttpRequestParser;

	@Autowired
	private PrincipalParser principalParser;

	@Autowired
	private WebSessionParser webSessionParser;

	@Autowired
	private MultiValueMapParser multiValueMapParser;

	@Autowired
	private PartParser partParser;

	@Autowired
	private ServerHttpResponseParser serverHttpResponseParser;

	@Autowired
	private ExApplicationContextRepoAsync exApplicationContextRepoAsync;

	@Autowired
	private InformationExchangeRepoAsync informationExchangeRepoAsync;

	private static boolean APPLICATION_CONTEXT_PERSISTED_FLAG = false;

	private String identifier() {

		return (String) MDC.get("identifier");
	}

	public void identifyAndPersist(ServerWebExchange serverWebExchange, ExchangeInformationType exchangeInformationType) {

		String identifier = identifier();
		log.debug("identify and persist -> (identifier) {} (exchangeInformationType) {}", identifier, exchangeInformationType);

		Mono<Boolean> monoCall = null;

		switch (exchangeInformationType) {

			case attributes :
				Map<String, Object> attributes = serverWebExchange.getAttributes();
				monoCall = informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), attributes);
				break;

			case logPrefix :
				String logPrefix = serverWebExchange.getLogPrefix();
				monoCall = informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), logPrefix);
				break;

			case notModifiedFlag :
				boolean notModifiedFlag = serverWebExchange.isNotModified();
				monoCall = informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), notModifiedFlag);
				break;

			case serverHttpRequest :
				ServerHttpRequest request = serverWebExchange.getRequest();
				monoCall = serverHttpRequestParser.parse(request)
					.flatMap(exServerHttpRequest -> {
						log.trace("got -> (exServerHttpRequest) {}", exServerHttpRequest);
						return informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), exServerHttpRequest);
					});
				break;

			case principal :
				monoCall = serverWebExchange.getPrincipal()
					.flux()
					.collectList()
					.flatMap(principalList -> {
						log.trace("got -> (principalList) {}", principalList);
						Principal principal = principalList.isEmpty() ? null : principalList.get(0);
						ExPrincipal exPrincipal = principalParser.parse(principal);
						return informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), exPrincipal);
					});
				break;

			case session :
				monoCall = serverWebExchange.getSession()
					.flux()
					.collectList()
					.flatMap(sessionList -> {
						log.trace("got -> (sessionList) {}", sessionList);
						WebSession session = sessionList.isEmpty() ? null : sessionList.get(0);
						ExWebSession exSession = webSessionParser.parse(session);
						return informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), exSession);
					});
				break;

			case formData :
				monoCall = serverWebExchange.getFormData()
					.flux()
					.collectList()
					.flatMap(formDataList -> {
						log.trace("got -> (formDataList) {}", formDataList);
						MultiValueMap<String, String> formData = formDataList.isEmpty() ? null : formDataList.get(0);
						ExMultiValueMap<String, String> exFormData = multiValueMapParser.parse(formData);
						return informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), exFormData);
					});
				break;

			case multipartData :
				monoCall = serverWebExchange.getMultipartData()
					.flux()
					.collectList()
					.flatMap(multipartDataList -> {
						log.trace("got -> (multipartDataList) {}", multipartDataList);
						MultiValueMap<String, Part> multipartData = multipartDataList.isEmpty() ? null : multipartDataList.get(0);
						ExMultiValueMap<String, Part> exMultiValueMap = multiValueMapParser.parse(multipartData);
						if (null != exMultiValueMap) {
							ExMultiValueMap<String, ExPart> exParts = new ExMultiValueMap<String, ExPart>();
							exParts.setClazz(exMultiValueMap.getClazz());
							exParts.setHashCode(exMultiValueMap.getHashCode());
							exParts.setToString(exMultiValueMap.getToString());
							exParts.setSize(exMultiValueMap.getSize());
							exParts.setEmpty(exMultiValueMap.isEmpty());
							Map<String, List<Part>> mapVsList = exMultiValueMap.getMapVsList();
							Map<String, List<ExPart>> mapVsExList = null;
							if (null != mapVsList) {
								mapVsExList = new TreeMap<String, List<ExPart>>();
								for (String key : mapVsList.keySet()) {
									List<Part> values = mapVsList.get(key);
									int size = (null != values) ? values.size() : 1;
									final List<ExPart> exValueList = new ArrayList<ExPart>(size);
									if (null != values) {
										int index = 0;
										for (Part part : values) {
											Mono<ExPart> exPartMono = partParser.parse(part)
												.map(exPart -> {
													exValueList.add(exPart);
													return exPart;
												});
											Thread helperThread = MonoCallSynchronousExecutor.waitForIt(exPartMono, "get part data for key: " + key + " index: " + index, 10);
											++index;
										}
									}
									mapVsExList.put(key, exValueList);
								}
							}
							exParts.setMapVsList(mapVsExList);
							return informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), exParts);
						} else {
							return Mono.just(false);
						}
					});
				break;

			case localeContext :
				LocaleContext localeContext = serverWebExchange.getLocaleContext();
				ExLocaleContext exLocaleContext = localeContextParser.parse(localeContext);
				monoCall = informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), exLocaleContext);
				break;

			case serverHttpResponse :
				ServerHttpResponse response = serverWebExchange.getResponse();
				ExServerHttpResponse exServerHttpResponse = serverHttpResponseParser.parse(response);
				monoCall = informationExchangeRepoAsync.updateAttribute(identifier, exchangeInformationType.name(), exServerHttpResponse);
				break;

			case applicationContext :
				if (APPLICATION_CONTEXT_PERSISTED_FLAG) {
					monoCall = Mono.just(false);
				} else {
					APPLICATION_CONTEXT_PERSISTED_FLAG = true;
					ApplicationContext applicationContext = serverWebExchange.getApplicationContext();
					ExApplicationContext exApplicationContext = applicationContextParser.parse(applicationContext);
					String documentId = exApplicationContext.getEnvironment().getActiveProfiles()[0] + "-" + exApplicationContext.getStartupDate();
					exApplicationContext.setDocumentId(documentId);
					monoCall = exApplicationContextRepoAsync.insert(exApplicationContext)
						.flatMap(persistedExApplicationContext -> {
							log.info("persisted -> (exApplicationContext) {}", persistedExApplicationContext);
							return Mono.just(true);
						});
				}
				break;

			default :
				break;
		}

		Thread helperThread = MonoCallSynchronousExecutor.waitForIt(monoCall, "web exchange update " + exchangeInformationType.name(), 50);
	}

}
