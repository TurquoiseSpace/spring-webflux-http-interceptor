package open.source.exchange.parser;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExLocaleLanguageRange;

@Log4j2
@Service
@RequiredArgsConstructor
public class LocaleLanguageRangeParser {

	@Autowired
	private ObjectParser objectParser;

	public ExLocaleLanguageRange parse(Locale.LanguageRange localeLanguageRange) {

		log.info("parse -> (localeLanguageRange) {}", localeLanguageRange);
		ExLocaleLanguageRange exLocaleLanguageRange = null;

		if (null != localeLanguageRange) {
			ExBase exBase = objectParser.parse(localeLanguageRange);
			exLocaleLanguageRange = new ExLocaleLanguageRange(exBase);

			String range = localeLanguageRange.getRange();
			exLocaleLanguageRange.setRange(range);

			double weight = localeLanguageRange.getWeight();
			exLocaleLanguageRange.setWeight(weight);
		}
		return exLocaleLanguageRange;
	}

}
