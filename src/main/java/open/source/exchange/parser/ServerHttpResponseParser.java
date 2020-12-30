package open.source.exchange.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExHttpCookie;
import open.source.exchange.model.ExHttpHeaders;
import open.source.exchange.model.ExHttpStatus;
import open.source.exchange.model.ExMultiValueMap;
import open.source.exchange.model.ExResponseCookie;
import open.source.exchange.model.ExServerHttpRequest;
import open.source.exchange.model.ExServerHttpResponse;

@Log4j2
@Service
@RequiredArgsConstructor
public class ServerHttpResponseParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private MultiValueMapParser multiValueMapParser;

	@Autowired
	private ResponseCookieParser responseCookieParser;

	@Autowired
	private HttpHeadersParser httpHeadersParser;

	@Autowired
	private HttpStatusParser httpStatusParser;

	public ExServerHttpResponse parse(ServerHttpResponse serverHttpResponse) {

		log.trace("parse -> (serverHttpResponse) {}", serverHttpResponse);
		ExServerHttpResponse exServerHttpResponse = null;

		if (null != serverHttpResponse) {
			ExBase exBase = objectParser.parse(serverHttpResponse);
			exServerHttpResponse = new ExServerHttpResponse(exBase);

			DataBufferFactory bufferFactory = serverHttpResponse.bufferFactory();
			// TODO : parse and build object

			MultiValueMap<String, ResponseCookie> cookies = serverHttpResponse.getCookies();
			ExMultiValueMap<String, ResponseCookie> exMultiValueMap = multiValueMapParser.parse(cookies);
			ExMultiValueMap<String, ExResponseCookie> exCookies = null;
			if (null != exMultiValueMap) {
				exCookies = new ExMultiValueMap<String, ExResponseCookie>();
				exCookies.setClazz(exMultiValueMap.getClazz());
				exCookies.setHashCode(exMultiValueMap.getHashCode());
				exCookies.setToString(exMultiValueMap.getToString());
				exCookies.setSize(exMultiValueMap.getSize());
				exCookies.setEmpty(exMultiValueMap.isEmpty());
				Map<String, List<ResponseCookie>> mapVsList = exMultiValueMap.getMapVsList();
				Map<String, List<ExResponseCookie>> mapVsExList = null;
				if (null != mapVsList) {
					mapVsExList = new TreeMap<String, List<ExResponseCookie>>();
					for (String key : mapVsList.keySet()) {
						List<ResponseCookie> values = mapVsList.get(key);
						List<ExResponseCookie> exValueList = null;
						if (null != values) {
							exValueList = new ArrayList<ExResponseCookie>();
							for (ResponseCookie responseCookie : values) {
								ExResponseCookie exResponseCookie = responseCookieParser.parse(responseCookie);
								exValueList.add(exResponseCookie);
							}
						}
						mapVsExList.put(key, exValueList);
					}
				}
				exCookies.setMapVsList(mapVsExList);
			}
			exServerHttpResponse.setCookies(exCookies);

			HttpHeaders headers = serverHttpResponse.getHeaders();
			ExHttpHeaders exHttpHeaders = httpHeadersParser.parse(headers);
			exServerHttpResponse.setHeaders(exHttpHeaders);

			HttpStatus statusCode = serverHttpResponse.getStatusCode();
			ExHttpStatus exHttpStatus = httpStatusParser.parse(statusCode);
			exServerHttpResponse.setStatusCode(exHttpStatus);

			boolean committedFlag = serverHttpResponse.isCommitted();
			exServerHttpResponse.setCommittedFlag(committedFlag);
		}
		return exServerHttpResponse;
	}

}
