package open.source.exchange.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExEnum;
import open.source.exchange.model.ExHttpMethod;
import open.source.exchange.model.ExServerHttpRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class HttpMethodParser {

	@Autowired
	private EnumParser enumParser;

	public ExHttpMethod parse(HttpMethod httpMethod) {

		log.trace("parse -> (httpMethod) {}", httpMethod);
		ExHttpMethod exHttpMethod = null;

		if (null != httpMethod) {
			ExEnum exEnum = enumParser.parse(httpMethod);
			exHttpMethod = new ExHttpMethod(exEnum);
		}
		return exHttpMethod;
	}

}
