package open.source.exchange.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.PathContainer.Element;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExElement;
import open.source.exchange.model.ExHttpMethod;
import open.source.exchange.model.ExPathContainer;
import open.source.exchange.model.ExRequestPath;

@Log4j2
@Service
@RequiredArgsConstructor
public class RequestPathParser {

	@Autowired
	private PathContainerParser pathContainerParser;

	public ExRequestPath parse(RequestPath requestPath) {

		log.info("parse -> (requestPath) {}", requestPath);
		ExRequestPath exRequestPath = null;

		if (null != requestPath) {
			ExPathContainer exPathContainer = pathContainerParser.parse(requestPath);
			exRequestPath = new ExRequestPath(exPathContainer);

			PathContainer contextPath = requestPath.contextPath();
			ExPathContainer exContextPath = pathContainerParser.parse(contextPath);
			exRequestPath.setContextPath(exContextPath);

			PathContainer pathWithinApplication = requestPath.pathWithinApplication();
			ExPathContainer exPathWithinApplication = pathContainerParser.parse(pathWithinApplication);
			exRequestPath.setPathWithinApplication(exPathWithinApplication);
		}
		return exRequestPath;
	}

}
