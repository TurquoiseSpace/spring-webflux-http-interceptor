package open.source.exchange.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExHttpCookie;
import open.source.exchange.model.ExServerHttpRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class HttpCookieParser {

	@Autowired
	private ObjectParser objectParser;

	public ExHttpCookie parse(HttpCookie httpCookie) {

		log.info("parse -> (httpCookie) {}", httpCookie);
		ExHttpCookie exHttpCookie = null;

		if (null != httpCookie) {
			ExBase exBase = objectParser.parse(httpCookie);
			exHttpCookie = new ExHttpCookie(exBase);

			String name = httpCookie.getName();
			exHttpCookie.setName(name);

			String value = httpCookie.getValue();
			exHttpCookie.setValue(value);
		}
		return exHttpCookie;
	}

}
