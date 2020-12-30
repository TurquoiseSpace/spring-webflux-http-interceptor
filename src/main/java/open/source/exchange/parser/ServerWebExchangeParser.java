package open.source.exchange.parser;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExLocaleContext;
import open.source.exchange.model.ExMultiValueMap;
import open.source.exchange.model.ExPart;
import open.source.exchange.model.ExPrincipal;
import open.source.exchange.model.ExServerHttpResponse;
import open.source.exchange.model.ExWebSession;
import open.source.exchange.model.Information;
import open.source.exchange.utility.asynchronous.MonoCallSynchronousExecutor;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
@Deprecated
public class ServerWebExchangeParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private ApplicationContextParser applicationContextParser;

	@Autowired
	private LocaleContextParser localeContextParser;

	@Autowired
	private ServerHttpRequestParser serverHttpRequestParser;

	@Autowired
	private ServerHttpResponseParser serverHttpResponseParser;

	@Autowired
	private PrincipalParser principalParser;

	@Autowired
	private WebSessionParser webSessionParser;

	@Autowired
	private MultiValueMapParser multiValueMapParser;

	@Autowired
	private PartParser partParser;

	private Mono<Information> parseAsyncState(ServerWebExchange serverWebExchange, Information information) {

		log.debug("parse async state -> (serverWebExchange) {} (information) {}", serverWebExchange, information);

		return serverWebExchange.getPrincipal()
			.flux()
			.collectList()
			.flatMap(principalList -> {
				log.debug("got -> (principalList) {}", principalList);
				Principal principal = principalList.isEmpty() ? null : principalList.get(0);
				ExPrincipal exPrincipal = principalParser.parse(principal);
				information.setPrincipal(exPrincipal);

				return serverWebExchange.getSession()
					.flux()
					.collectList()
					.flatMap(sessionList -> {
						log.debug("got -> (sessionList) {}", sessionList);
						WebSession session = sessionList.isEmpty() ? null : sessionList.get(0);
						ExWebSession exSession = webSessionParser.parse(session);
						information.setSession(exSession);

						return serverWebExchange.getFormData()
							.flux()
							.collectList()
							.flatMap(formDataList -> {
								log.debug("got -> (formDataList) {}", formDataList);
								MultiValueMap<String, String> formData = formDataList.isEmpty() ? null : formDataList.get(0);
								ExMultiValueMap<String, String> exFormData = multiValueMapParser.parse(formData);
								information.setFormData(exFormData);

								return serverWebExchange.getMultipartData()
									.flux()
									.collectList()
									.flatMap(multipartDataList -> {
										log.debug("got -> (multipartDataList) {}", multipartDataList);
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
															try {
																helperThread.join();
															} catch (InterruptedException e) {
																log.error("interrupted", e);
															}
															++index;
														}
													}
													mapVsExList.put(key, exValueList);
												}
											}
											exParts.setMapVsList(mapVsExList);
											information.setMultipartData(exParts);
										}

										ServerHttpRequest request = serverWebExchange.getRequest();
										return serverHttpRequestParser.parse(request)
											.flatMap(exServerHttpRequest -> {
												information.setServerHttpRequest(exServerHttpRequest);

												return Mono.just(information);
											});
									});
							});
					});
			});
	}

	private void parseSyncState(ServerWebExchange serverWebExchange, Information information) {

		log.debug("parse sync state -> (serverWebExchange) {} (information) {}", serverWebExchange, information);

		ApplicationContext applicationContext = serverWebExchange.getApplicationContext();
		ExApplicationContext exApplicationContext = applicationContextParser.parse(applicationContext);
		//information.setApplicationContext(exApplicationContext);

		Map<String, Object> attributes = serverWebExchange.getAttributes();
		information.setAttributes(attributes);

		LocaleContext localeContext = serverWebExchange.getLocaleContext();
		ExLocaleContext exLocaleContext = localeContextParser.parse(localeContext);
		information.setLocaleContext(exLocaleContext);

		String logPrefix = serverWebExchange.getLogPrefix();
		information.setLogPrefix(logPrefix);

		ServerHttpResponse response = serverWebExchange.getResponse();
		ExServerHttpResponse exServerHttpResponse = serverHttpResponseParser.parse(response);
		information.setServerHttpResponse(exServerHttpResponse);

		boolean notModifiedFlag = serverWebExchange.isNotModified();
		information.setNotModifiedFlag(notModifiedFlag);
	}

	/**
	 * 
	 * @param serverWebExchange
	 * @throws StackOverflowError
	 * @return
	 */
	@Deprecated
	public Mono<Information> parse(ServerWebExchange serverWebExchange) {

		log.debug("parse -> (serverWebExchange) {}", serverWebExchange);
		Information information = new Information();

		if (null == serverWebExchange) {
			return Mono.just(information);
		} else {
			ExBase exBase = objectParser.parse(serverWebExchange);
			information = new Information(exBase);

			parseSyncState(serverWebExchange, information);
			return parseAsyncState(serverWebExchange, information);
		}
	}

}
