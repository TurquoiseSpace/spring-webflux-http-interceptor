package open.source.parser;

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
import open.source.model.ExBase;
import open.source.model.ExHttpCookie;
import open.source.model.ExHttpHeaders;
import open.source.model.ExHttpStatus;
import open.source.model.ExMultiValueMap;
import open.source.model.ExResponseCookie;
import open.source.model.ExServerHttpRequest;
import open.source.model.ExServerHttpResponse;

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

		log.info("parse -> (serverHttpResponse) {}", serverHttpResponse);
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
