package open.source.exchange.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEnum;
import open.source.exchange.model.ExHttpStatus;
import open.source.exchange.model.ExSeries;

@Log4j2
@Service
@RequiredArgsConstructor
public class HttpStatusParser {

	@Autowired
	private EnumParser enumParser;

	@Autowired
	private SeriesParser seriesParser;

	public ExHttpStatus parse(HttpStatus httpStatus) {

		log.info("parse -> (httpStatus) {}", httpStatus);
		ExHttpStatus exHttpStatus = null;

		if (null != httpStatus) {
			ExEnum exEnum = enumParser.parse(httpStatus);
			exHttpStatus = new ExHttpStatus(exEnum);

			int value = httpStatus.value();
			exHttpStatus.setValue(value);

			String reasonPhrase = httpStatus.getReasonPhrase();
			exHttpStatus.setReasonPhrase(reasonPhrase);

			boolean is1xxInformational = httpStatus.is1xxInformational();
			exHttpStatus.set1xxInformational(is1xxInformational);

			boolean is2xxSuccessful = httpStatus.is2xxSuccessful();
			exHttpStatus.set2xxSuccessful(is2xxSuccessful);

			boolean is3xxRedirection = httpStatus.is3xxRedirection();
			exHttpStatus.set3xxRedirection(is3xxRedirection);

			boolean is4xxClientError = httpStatus.is4xxClientError();
			exHttpStatus.set4xxClientError(is4xxClientError);

			boolean is5xxServerError = httpStatus.is5xxServerError();
			exHttpStatus.set5xxServerError(is5xxServerError);

			boolean isError = httpStatus.isError();
			exHttpStatus.setError(isError);

			Series series = httpStatus.series();
			ExSeries exSeries = seriesParser.parse(series);
			exHttpStatus.setSeries(exSeries);
		}
		return exHttpStatus;
	}

}
