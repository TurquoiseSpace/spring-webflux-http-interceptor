package open.source.parser;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.SslInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExBase;
import open.source.model.ExHttpCookie;
import open.source.model.ExHttpHeaders;
import open.source.model.ExHttpMethod;
import open.source.model.ExInetSocketAddress;
import open.source.model.ExMultiValueMap;
import open.source.model.ExRequestPath;
import open.source.model.ExServerHttpRequest;
import open.source.model.ExSslInfo;
import open.source.model.ExURI;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class ServerHttpRequestParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private MultiValueMapParser multiValueMapParser;

	@Autowired
	private HttpCookieParser httpCookieParser;

	@Autowired
	private HttpHeadersParser httpHeadersParser;

	@Autowired
	private HttpMethodParser httpMethodParser;

	@Autowired
	private RequestPathParser requestPathParser;

	@Autowired
	private InetSocketAddressParser inetSocketAddressParser;

	@Autowired
	private SslInfoParser sslInfoParser;

	@Autowired
	private URIParser uriParser;

	@Autowired
	private DataBufferListParser dataBufferListParser;

	private Mono<ExServerHttpRequest> parseAsyncState(ServerHttpRequest serverHttpRequest, ExServerHttpRequest exServerHttpRequest) {

		log.info("parse async state -> (serverHttpRequest) {} (exServerHttpRequest) {}", serverHttpRequest, exServerHttpRequest);

		// TODO : fix it
		// https://stackoverflow.com/questions/45240005/how-to-log-request-and-response-bodies-in-spring-webflux
		// java.lang.IllegalStateException: Only one connection receive subscriber allowed.
		// https://blog.csdn.net/u011084148/article/details/100144727
		/*
		return serverHttpRequest.getBody()
			.collectList()
			.map(dataBufferList -> {
				String completeDataBufferString = dataBufferListParser.parse(dataBufferList);
				exServerHttpRequest.setBody(completeDataBufferString);
				return exServerHttpRequest;
			});
		*/
		//Flux<DataBuffer> body = serverHttpRequest.getBody();
		//body.collectList().doOnNext(onNext);
		//body.collectList().doOnSuccess(onSuccess);
		//body.collectList().doOnSuccessOrError(onSuccessOrError);
		return Mono.just(exServerHttpRequest);
	}

	private void parseSyncState(ServerHttpRequest serverHttpRequest, ExServerHttpRequest exServerHttpRequest) {

		log.info("parse sync state -> (serverHttpRequest) {} (exServerHttpRequest) {}", serverHttpRequest, exServerHttpRequest);

		MultiValueMap<String, HttpCookie> cookies = serverHttpRequest.getCookies();
		ExMultiValueMap<String, HttpCookie> exMultiValueMap = multiValueMapParser.parse(cookies);
		ExMultiValueMap<String, ExHttpCookie> exCookies = null;
		if (null != exMultiValueMap) {
			exCookies = new ExMultiValueMap<String, ExHttpCookie>();
			exCookies.setClazz(exMultiValueMap.getClazz());
			exCookies.setHashCode(exMultiValueMap.getHashCode());
			exCookies.setToString(exMultiValueMap.getToString());
			exCookies.setSize(exMultiValueMap.getSize());
			exCookies.setEmpty(exMultiValueMap.isEmpty());
			Map<String, List<HttpCookie>> mapVsList = exMultiValueMap.getMapVsList();
			Map<String, List<ExHttpCookie>> mapVsExList = null;
			if (null != mapVsList) {
				mapVsExList = new TreeMap<String, List<ExHttpCookie>>();
				for (String key : mapVsList.keySet()) {
					List<HttpCookie> values = mapVsList.get(key);
					List<ExHttpCookie> exValueList = null;
					if (null != values) {
						exValueList = new ArrayList<ExHttpCookie>();
						for (HttpCookie httpCookie : values) {
							ExHttpCookie exHttpCookie = httpCookieParser.parse(httpCookie);
							exValueList.add(exHttpCookie);
						}
					}
					mapVsExList.put(key, exValueList);
				}
			}
			exCookies.setMapVsList(mapVsExList);
		}
		exServerHttpRequest.setCookies(exCookies);

		HttpHeaders headers = serverHttpRequest.getHeaders();
		ExHttpHeaders exHttpHeaders = httpHeadersParser.parse(headers);
		exServerHttpRequest.setHeaders(exHttpHeaders);

		String id = serverHttpRequest.getId();
		exServerHttpRequest.setId(id);

		HttpMethod method = serverHttpRequest.getMethod();
		ExHttpMethod exHttpMethod = httpMethodParser.parse(method);
		exServerHttpRequest.setMethod(exHttpMethod);

		String methodValue = serverHttpRequest.getMethodValue();
		exServerHttpRequest.setMethodValue(methodValue);

		RequestPath path = serverHttpRequest.getPath();
		ExRequestPath exRequestPath = requestPathParser.parse(path);
		exServerHttpRequest.setPath(exRequestPath);

		MultiValueMap<String, String> queryParams = serverHttpRequest.getQueryParams();
		ExMultiValueMap<String, String> exQueryParams = multiValueMapParser.parse(queryParams);
		exServerHttpRequest.setQueryParams(exQueryParams);

		InetSocketAddress remoteAddress = serverHttpRequest.getRemoteAddress();
		ExInetSocketAddress exInetSocketAddress = inetSocketAddressParser.parse(remoteAddress);
		exServerHttpRequest.setRemoteAddress(exInetSocketAddress);

		SslInfo sslInfo = serverHttpRequest.getSslInfo();
		ExSslInfo exSslInfo = sslInfoParser.parse(sslInfo);
		exServerHttpRequest.setSslInfo(exSslInfo);

		URI uri = serverHttpRequest.getURI();
		ExURI exURI = uriParser.parse(uri);
		exServerHttpRequest.setUri(exURI);
	}

	public Mono<ExServerHttpRequest> parse(ServerHttpRequest serverHttpRequest) {

		log.info("parse -> (serverHttpRequest) {}", serverHttpRequest);
		ExServerHttpRequest exServerHttpRequest = new ExServerHttpRequest();

		if (null == serverHttpRequest) {
			return Mono.just(exServerHttpRequest);
		} else {
			ExBase exBase = objectParser.parse(serverHttpRequest);
			exServerHttpRequest = new ExServerHttpRequest(exBase);

			parseSyncState(serverHttpRequest, exServerHttpRequest);
			return parseAsyncState(serverHttpRequest, exServerHttpRequest);
		}
	}

}
