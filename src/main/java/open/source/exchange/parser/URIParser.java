package open.source.exchange.parser;

import java.net.MalformedURLException;
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
public class URIParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private URLParser urlParser;

	private ExURI parseCore(URI uri) {

		ExBase exBase = objectParser.parse(uri);
		ExURI exURI = new ExURI(exBase);

		String authority = uri.getAuthority();
		exURI.setAuthority(authority);

		String fragment = uri.getFragment();
		exURI.setFragment(fragment);

		String host = uri.getHost();
		exURI.setHost(host);

		String path = uri.getPath();
		exURI.setPath(path);

		int port = uri.getPort();
		exURI.setPort(port);

		String query = uri.getQuery();
		exURI.setQuery(query);

		String rawAuthority = uri.getRawAuthority();
		exURI.setRawAuthority(rawAuthority);

		String rawFragment = uri.getRawFragment();
		exURI.setRawFragment(rawFragment);

		String rawPath = uri.getRawPath();
		exURI.setRawPath(rawPath);

		String rawQuery = uri.getRawQuery();
		exURI.setRawQuery(rawQuery);

		String rawSchemeSpecificPart = uri.getRawSchemeSpecificPart();
		exURI.setRawSchemeSpecificPart(rawSchemeSpecificPart);

		String rawUserInfo = uri.getRawUserInfo();
		exURI.setRawUserInfo(rawUserInfo);

		String scheme = uri.getScheme();
		exURI.setScheme(scheme);

		String schemeSpecificPart = uri.getSchemeSpecificPart();
		exURI.setSchemeSpecificPart(schemeSpecificPart);

		String userInfo = uri.getUserInfo();
		exURI.setUserInfo(userInfo);

		boolean absoluteFlag = uri.isAbsolute();
		exURI.setAbsoluteFlag(absoluteFlag);

		boolean opaqueFlag = uri.isOpaque();
		exURI.setOpaqueFlag(opaqueFlag);

		String toASCIIString = uri.toASCIIString();
		exURI.setToASCIIString(toASCIIString);

		try {
			URL url = uri.toURL();
			ExURL exURL = urlParser.parse(url);
			exURI.setUrl(exURL);
		} catch (MalformedURLException e) {
			log.error("error identifying url", e);
		}

		return exURI;
	}

	public ExURI parse(URI uri) {

		log.trace("parse -> (uri) {}", uri);
		ExURI exURI = null;

		if (null != uri) {
			exURI = parseCore(uri);

			URI normalize = uri.normalize();
			ExURI exNormalize = parseCore(normalize);
			exURI.setNormalize(exNormalize);

			try {
				URI serverAuthority = uri.parseServerAuthority();
				ExURI exServerAuthority = parseCore(serverAuthority);
				exURI.setServerAuthority(exServerAuthority);
			} catch (URISyntaxException e) {
				log.error("error parsing uri server authority", e);
			}
		}
		return exURI;
	}

}
