package open.source.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExEnum;
import open.source.model.ExHttpMethod;
import open.source.model.ExServerHttpRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class HttpMethodParser {

	@Autowired
	private EnumParser enumParser;

	public ExHttpMethod parse(HttpMethod httpMethod) {

		log.info("parse -> (httpMethod) {}", httpMethod);
		ExHttpMethod exHttpMethod = null;

		if (null != httpMethod) {
			ExEnum exEnum = enumParser.parse(httpMethod);
			exHttpMethod = new ExHttpMethod(exEnum);
		}
		return exHttpMethod;
	}

}
