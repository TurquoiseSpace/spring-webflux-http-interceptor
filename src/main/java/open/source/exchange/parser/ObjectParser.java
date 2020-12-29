package open.source.exchange.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;

@Log4j2
@Service
@RequiredArgsConstructor
public class ObjectParser {

	@Autowired
	private ClassParser classParser;

	public ExBase parse(Object object) {

		log.info("parse -> (object) {}", object);
		ExBase exBase = null;

		if (null != object) {
			exBase = new ExBase();

			int hashCode = object.hashCode();
			exBase.setHashCode(hashCode);

			String toString = object.toString();
			exBase.setToString(toString);

			Class<? extends Object> clazz = object.getClass();
			// classParser.parse(Object.class);
			Object classObject = classParser.parse(clazz);
			exBase.setClazz(clazz.getName());
		}
		return exBase;
	}

}
