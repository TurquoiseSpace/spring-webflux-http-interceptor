package open.source.exchange.parser;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExDuration;
import open.source.exchange.model.ExHttpCookie;
import open.source.exchange.model.ExResponseCookie;

@Log4j2
@Service
@RequiredArgsConstructor
public class ResponseCookieParser {

	@Autowired
	private HttpCookieParser httpCookieParser;

	@Autowired
	private DurationParser durationParser;

	public ExResponseCookie parse(ResponseCookie responseCookie) {

		log.info("parse -> (responseCookie) {}", responseCookie);
		ExResponseCookie exResponseCookie = null;

		if (null != responseCookie) {
			ExHttpCookie exHttpCookie = httpCookieParser.parse(responseCookie);
			exResponseCookie = new ExResponseCookie(exHttpCookie);

			String domain = responseCookie.getDomain();
			exResponseCookie.setDomain(domain);

			Duration maxAge = responseCookie.getMaxAge();
			ExDuration exMaxAge = durationParser.parse(maxAge);
			exResponseCookie.setMaxAge(exMaxAge);

			String path = responseCookie.getPath();
			exResponseCookie.setPath(path);

			String sameSite = responseCookie.getSameSite();
			exResponseCookie.setSameSite(sameSite);

			boolean httpOnlyFlag = responseCookie.isHttpOnly();
			exResponseCookie.setHttpOnlyFlag(httpOnlyFlag);

			boolean secureFlag = responseCookie.isSecure();
			exResponseCookie.setSecureFlag(secureFlag);
		}
		return exResponseCookie;
	}

}
