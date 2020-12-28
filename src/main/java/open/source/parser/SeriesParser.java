package open.source.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExEnum;
import open.source.model.ExSeries;

@Log4j2
@Service
@RequiredArgsConstructor
public class SeriesParser {

	@Autowired
	private EnumParser enumParser;

	public ExSeries parse(Series series) {

		log.info("parse -> (series) {}", series);
		ExSeries exSeries = null;

		if (null != series) {
			ExEnum exEnum = enumParser.parse(series);
			exSeries = new ExSeries(exEnum);

			int value = series.value();
			exSeries.setValue(value);
		}
		return exSeries;
	}

}
