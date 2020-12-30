package open.source.exchange.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.PathContainer.Element;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExElement;
import open.source.exchange.model.ExPathContainer;

@Log4j2
@Service
@RequiredArgsConstructor
public class PathContainerParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private ElementParser elementParser;

	public ExPathContainer parse(PathContainer pathContainer) {

		log.trace("parse -> (pathContainer) {}", pathContainer);
		ExPathContainer exPathContainer = null;

		if (null != pathContainer) {
			ExBase exBase = objectParser.parse(pathContainer);
			exPathContainer = new ExPathContainer(exBase);

			List<Element> elements = pathContainer.elements();
			List<ExElement> exElementList = new ArrayList<ExElement>();
			if (null != elements) {
				for (Element element : elements) {
					ExElement exElement = elementParser.parse(element);
					exElementList.add(exElement);
				}
			}
			exPathContainer.setElements(exElementList);

			String value = pathContainer.value();
			exPathContainer.setValue(value);
		}
		return exPathContainer;
	}

}
