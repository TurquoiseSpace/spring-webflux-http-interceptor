package open.source.exchange.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExURI;
import open.source.exchange.model.ExURL;

@Log4j2
@Service
@RequiredArgsConstructor
public class URLParser {

	@Autowired
	private ObjectParser objectParser;

	public ExURL parse(URL url) {

		log.debug("parse -> (url) {}", url);
		ExURL exURL = null;

		if (null != url) {
			ExBase exBase = objectParser.parse(url);
			exURL = new ExURL(exBase);

			String authority = url.getAuthority();
			exURL.setAuthority(authority);

			int defaultPort = url.getDefaultPort();
			exURL.setDefaultPort(defaultPort);

			String file = url.getFile();
			exURL.setFile(file);

			String host = url.getHost();
			exURL.setHost(host);

			String path = url.getPath();
			exURL.setPath(path);

			int port = url.getPort();
			exURL.setPort(port);

			String protocol = url.getProtocol();
			exURL.setProtocol(protocol);

			String query = url.getQuery();
			exURL.setQuery(query);

			String ref = url.getRef();
			exURL.setRef(ref);

			String userInfo = url.getUserInfo();
			exURL.setUserInfo(userInfo);

			String toExternalForm = url.toExternalForm();
			exURL.setToExternalForm(toExternalForm);

			// Note : uncommenting below will start infinite recursive loop
			// url.getContent();
			// url.openConnection();
			// url.openStream();
			// url.toURI();
		}
		return exURL;
	}

}
