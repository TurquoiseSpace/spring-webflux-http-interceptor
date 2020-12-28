package open.source.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.PathContainer.Element;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExBase;
import open.source.model.ExElement;

@Log4j2
@Service
@RequiredArgsConstructor
public class ElementParser {

	@Autowired
	private ObjectParser objectParser;

	public ExElement parse(Element element) {

		log.info("parse -> (element) {}", element);
		ExElement exElement = null;

		if (null != element) {
			ExBase exBase = objectParser.parse(element);
			exElement = new ExElement(exBase);

			String value = element.value();
			exElement.setValue(value);
		}
		return exElement;
	}

}
