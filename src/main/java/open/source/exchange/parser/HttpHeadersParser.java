package open.source.exchange.parser;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRange;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExCharset;
import open.source.exchange.model.ExContentDisposition;
import open.source.exchange.model.ExHttpHeaders;
import open.source.exchange.model.ExHttpMethod;
import open.source.exchange.model.ExInetSocketAddress;
import open.source.exchange.model.ExLocale;
import open.source.exchange.model.ExLocaleLanguageRange;
import open.source.exchange.model.ExMediaType;
import open.source.exchange.model.ExMultiValueMap;
import open.source.exchange.model.ExURI;

@Log4j2
@Service
@RequiredArgsConstructor
public class HttpHeadersParser {

	@Autowired
	private MultiValueMapParser multiValueMapParser;

	@Autowired
	private MediaTypeParser mediaTypeParser;

	@Autowired
	private CharsetParser charsetParser;

	@Autowired
	private LocaleLanguageRangeParser localeLanguageRangeParser;

	@Autowired
	private LocaleParser localeParser;

	@Autowired
	private HttpMethodParser httpMethodParser;

	@Autowired
	private ContentDispositionParser contentDispositionParser;

	@Autowired
	private InetSocketAddressParser inetSocketAddressParser;

	@Autowired
	private URIParser uriParser;

	@Autowired
	private ObjectParser objectParser;

	public ExHttpHeaders parse(HttpHeaders httpHeaders) {

		log.info("parse -> (httpHeaders) {}", httpHeaders);
		ExHttpHeaders exHttpHeaders = null;

		if (null != httpHeaders) {
			ExMultiValueMap<String, String> exMultiValueMap = multiValueMapParser.parse(httpHeaders);
			exHttpHeaders = new ExHttpHeaders(exMultiValueMap);

			List<MediaType> accept = httpHeaders.getAccept();
			if (null != accept && !accept.isEmpty()) {
				List<ExMediaType> exList = new ArrayList<ExMediaType>();
				for (MediaType mediaType : accept) {
					ExMediaType exMediaType = mediaTypeParser.parse(mediaType);
					exList.add(exMediaType);
				}
				exHttpHeaders.setAccept(exList);
			}

			List<Charset> acceptCharset = httpHeaders.getAcceptCharset();
			if (null != acceptCharset && !acceptCharset.isEmpty()) {
				List<ExCharset> exList = new ArrayList<ExCharset>();
				for (Charset charset : acceptCharset) {
					ExCharset exCharset = charsetParser.parse(charset);
					exList.add(exCharset);
				}
				exHttpHeaders.setAcceptCharset(exList);
			}

			List<Locale.LanguageRange> acceptLanguage = httpHeaders.getAcceptLanguage();
			if (null != acceptLanguage) {
				List<ExLocaleLanguageRange> exList = new ArrayList<ExLocaleLanguageRange>();
				for (Locale.LanguageRange localeLanguageRange : acceptLanguage) {
					ExLocaleLanguageRange exLocaleLanguageRange = localeLanguageRangeParser.parse(localeLanguageRange);
					exList.add(exLocaleLanguageRange);
				}
				exHttpHeaders.setAcceptLanguage(exList);
			}

			List<Locale> acceptLanguageAsLocales = httpHeaders.getAcceptLanguageAsLocales();
			if (null != acceptLanguageAsLocales) {
				List<ExLocale> exLocaleList = new ArrayList<ExLocale>();
				for (Locale locale : acceptLanguageAsLocales) {
					ExLocale exLocale = localeParser.parse(locale);
					exLocaleList.add(exLocale);
				}
				exHttpHeaders.setAcceptLanguageAsLocales(exLocaleList);
			}

			boolean accessControlAllowCredentials = httpHeaders.getAccessControlAllowCredentials();
			exHttpHeaders.setAccessControlAllowCredentials(accessControlAllowCredentials);

			List<String> accessControlAllowHeaders = httpHeaders.getAccessControlAllowHeaders();
			exHttpHeaders.setAccessControlAllowHeaders(accessControlAllowHeaders);

			List<HttpMethod> accessControlAllowMethods = httpHeaders.getAccessControlAllowMethods();
			List<ExHttpMethod> exAccessControlAllowMethods = null;
			if (null != accessControlAllowMethods && !accessControlAllowMethods.isEmpty()) {
				exAccessControlAllowMethods = new ArrayList<ExHttpMethod>();
				for (HttpMethod httpMethod : accessControlAllowMethods) {
					ExHttpMethod exHttpMethod = httpMethodParser.parse(httpMethod);
					exAccessControlAllowMethods.add(exHttpMethod);
				}
			}
			exHttpHeaders.setAccessControlAllowMethods(exAccessControlAllowMethods);

			String accessControlAllowOrigin = httpHeaders.getAccessControlAllowOrigin();
			exHttpHeaders.setAccessControlAllowOrigin(accessControlAllowOrigin);

			List<String> accessControlExposeHeaders = httpHeaders.getAccessControlExposeHeaders();
			exHttpHeaders.setAccessControlExposeHeaders(accessControlExposeHeaders);

			long accessControlMaxAge = httpHeaders.getAccessControlMaxAge();
			exHttpHeaders.setAccessControlMaxAge(accessControlMaxAge);

			List<String> accessControlRequestHeaders = httpHeaders.getAccessControlRequestHeaders();
			exHttpHeaders.setAccessControlRequestHeaders(accessControlRequestHeaders);

			HttpMethod accessControlRequestMethod = httpHeaders.getAccessControlRequestMethod();
			ExHttpMethod exAccessControlRequestMethod = httpMethodParser.parse(accessControlRequestMethod);
			exHttpHeaders.setAccessControlRequestMethod(exAccessControlRequestMethod);

			Set<HttpMethod> allow = httpHeaders.getAllow();
			Set<ExHttpMethod> exAllow = null;
			if (null != allow && !allow.isEmpty()) {
				exAllow = new HashSet<ExHttpMethod>();
				for (HttpMethod httpMethod : allow) {
					ExHttpMethod exHttpMethod = httpMethodParser.parse(httpMethod);
					exAllow.add(exHttpMethod);
				}
			}
			exHttpHeaders.setAllow(exAllow);

			String cacheControl = httpHeaders.getCacheControl();
			exHttpHeaders.setCacheControl(cacheControl);

			List<String> connection = httpHeaders.getConnection();
			exHttpHeaders.setConnection(connection);

			ContentDisposition contentDisposition = httpHeaders.getContentDisposition();
			ExContentDisposition exContentDisposition = contentDispositionParser.parse(contentDisposition);
			exHttpHeaders.setContentDisposition(exContentDisposition);

			Locale contentLanguage = httpHeaders.getContentLanguage();
			ExLocale exContentLanguage = localeParser.parse(contentLanguage);
			exHttpHeaders.setContentLanguage(exContentLanguage);

			long contentLength = httpHeaders.getContentLength();
			exHttpHeaders.setContentLength(contentLength);

			MediaType contentType = httpHeaders.getContentType();
			ExMediaType exContentType = mediaTypeParser.parse(contentType);
			exHttpHeaders.setContentType(exContentType);

			long date = httpHeaders.getDate();
			exHttpHeaders.setDate(date);

			String eTag = httpHeaders.getETag();
			exHttpHeaders.setETag(eTag);

			long expires = httpHeaders.getExpires();
			exHttpHeaders.setExpires(expires);

			InetSocketAddress host = httpHeaders.getHost();
			ExInetSocketAddress exHost = inetSocketAddressParser.parse(host);
			exHttpHeaders.setHost(exHost);

			List<String> ifMatch = httpHeaders.getIfMatch();
			exHttpHeaders.setIfMatch(ifMatch);

			long ifModifiedSince = httpHeaders.getIfModifiedSince();
			exHttpHeaders.setIfModifiedSince(ifModifiedSince);

			List<String> ifNoneMatch = httpHeaders.getIfNoneMatch();
			exHttpHeaders.setIfNoneMatch(ifNoneMatch);

			long ifUnmodifiedSince = httpHeaders.getIfUnmodifiedSince();
			exHttpHeaders.setIfUnmodifiedSince(ifUnmodifiedSince);

			long lastModified = httpHeaders.getLastModified();
			exHttpHeaders.setLastModified(lastModified);

			URI location = httpHeaders.getLocation();
			ExURI exLocation = uriParser.parse(location);
			exHttpHeaders.setLocation(exLocation);

			String origin = httpHeaders.getOrigin();
			exHttpHeaders.setOrigin(origin);

			String pragma = httpHeaders.getPragma();
			exHttpHeaders.setPragma(pragma);

			List<HttpRange> range = httpHeaders.getRange();
			if (null != range && !range.isEmpty()) {
				List<ExBase> exList = new ArrayList<ExBase>();
				for (HttpRange httpRange : range) {
					ExBase exBase = objectParser.parse(httpRange);
					exList.add(exBase);
				}
				exHttpHeaders.setRange(exList);
			}

			String upgrade = httpHeaders.getUpgrade();
			exHttpHeaders.setUpgrade(upgrade);

			List<String> vary = httpHeaders.getVary();
			exHttpHeaders.setVary(vary);
		}
		return exHttpHeaders;
	}

}
