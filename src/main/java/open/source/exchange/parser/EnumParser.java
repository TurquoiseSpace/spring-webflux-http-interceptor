package open.source.exchange.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEnum;
import open.source.exchange.model.ExHttpMethod;
import open.source.exchange.model.ExServerHttpRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class EnumParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private ClassParser classParser;

	public ExEnum parse(Enum<?> enumeration) {

		log.info("parse -> (enumeration) {}", enumeration);
		ExEnum exEnum = null;

		if (null != enumeration) {
			ExBase exBase = objectParser.parse(enumeration);
			exEnum = new ExEnum(exBase);

			Class<?> declaringClass = enumeration.getDeclaringClass();
			Object declaringClassObject = classParser.parse(declaringClass);
			exEnum.setDeclaringClass(declaringClass.getName());

			String name = enumeration.name();
			exEnum.setName(name);

			int ordinal = enumeration.ordinal();
			exEnum.setOrdinal(ordinal);
		}
		return exEnum;
	}

}
