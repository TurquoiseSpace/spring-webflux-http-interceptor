package open.source.exchange.parser;

import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExLocale;

@Log4j2
@Service
@RequiredArgsConstructor
public class LocaleParser {

	@Autowired
	private ObjectParser objectParser;

	private ExLocale parseCore(Locale locale) {

		log.debug("parse core -> (locale) {}", locale);
		ExLocale exLocale = null;

		if (null != locale) {
			ExBase exBase = objectParser.parse(locale);
			exLocale = new ExLocale(exBase);

			String country = locale.getCountry();
			exLocale.setCountry(country);

			String displayCountry = locale.getDisplayCountry();
			exLocale.setDisplayCountry(displayCountry);

			String displayLanguage = locale.getDisplayLanguage();
			exLocale.setDisplayLanguage(displayLanguage);

			String displayName = locale.getDisplayName();
			exLocale.setDisplayName(displayName);

			String displayScript = locale.getDisplayScript();
			exLocale.setDisplayScript(displayScript);

			String displayVariant = locale.getDisplayVariant();
			exLocale.setDisplayVariant(displayVariant);

			Set<Character> extensionKeys = locale.getExtensionKeys();
			exLocale.setExtensionKeys(extensionKeys);

			String iso3Country = locale.getISO3Country();
			exLocale.setIso3Country(iso3Country);

			String iso3Language = locale.getISO3Language();
			exLocale.setIso3Language(iso3Language);

			String language = locale.getLanguage();
			exLocale.setLanguage(language);

			String script = locale.getScript();
			exLocale.setScript(script);

			Set<String> unicodeLocaleAttributes = locale.getUnicodeLocaleAttributes();
			exLocale.setUnicodeLocaleAttributes(unicodeLocaleAttributes);

			Set<String> unicodeLocaleKeys = locale.getUnicodeLocaleKeys();
			exLocale.setUnicodeLocaleKeys(unicodeLocaleKeys);

			String variant = locale.getVariant();
			exLocale.setVariant(variant);

			boolean hasExtensions = locale.hasExtensions();
			exLocale.setHasExtensions(hasExtensions);

			String toLanguageTag = locale.toLanguageTag();
			exLocale.setToLanguageTag(toLanguageTag);
		}
		return exLocale;
	}

	private ExLocale parseNonCore(Locale locale, ExLocale exLocale) {

		log.debug("parse non core -> (locale) {} (exLocale) {}", locale, exLocale);

		if (null != exLocale) {
			Locale stripExtensions = locale.stripExtensions();
			ExLocale exStripExtensions = parseCore(stripExtensions);
			exLocale.setStripExtensions(exStripExtensions);
		}
		return exLocale;
	}

	public ExLocale parse(Locale locale) {

		log.debug("parse -> (locale) {}", locale);
		ExLocale exLocale = parseCore(locale);
		return parseNonCore(locale, exLocale);
	}

}
