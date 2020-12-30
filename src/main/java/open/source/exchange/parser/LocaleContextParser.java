package open.source.exchange.parser;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExLocale;
import open.source.exchange.model.ExLocaleContext;

@Log4j2
@Service
@RequiredArgsConstructor
public class LocaleContextParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private LocaleParser localeParser;

	public ExLocaleContext parse(LocaleContext localeContext) {

		log.trace("parse -> (localeContext) {}", localeContext);
		ExLocaleContext exLocaleContext = null;

		if (null != localeContext) {
			ExBase exBase = objectParser.parse(localeContext);
			exLocaleContext = new ExLocaleContext(exBase);

			Locale locale = localeContext.getLocale();
			ExLocale exLocale = localeParser.parse(locale);
			exLocaleContext.setLocale(exLocale);
		}
		return exLocaleContext;
	}

}
