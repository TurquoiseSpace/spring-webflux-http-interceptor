package open.source.exchange.parser;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExDate;
import open.source.exchange.model.ExInstant;
import open.source.exchange.model.ExPublicKey;

@Log4j2
@Service
@RequiredArgsConstructor
public class DateParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private InstantParser instantParser;

	public ExDate parse(Date date) {

		log.info("parse -> (date) {}", date);
		ExDate exDate = null;

		if (null != date) {
			ExBase exBase = objectParser.parse(date);
			exDate = new ExDate(exBase);

			long timestamp = date.getTime();
			exDate.setTimestamp(timestamp);

			String toGMTString = date.toGMTString();
			exDate.setToGMTString(toGMTString);

			int timezoneOffset = date.getTimezoneOffset();
			exDate.setTimezoneOffset(timezoneOffset);

			String toLocaleString = date.toLocaleString();
			exDate.setToLocaleString(toLocaleString);

			int year = date.getYear();
			exDate.setYear(year);

			int month = date.getMonth();
			exDate.setMonth(month);

			int day = date.getDate();
			exDate.setDay(day);

			int weekday = date.getDay();
			exDate.setWeekday(weekday);

			int hour = date.getHours();
			exDate.setHour(hour);

			int minute = date.getMinutes();
			exDate.setMinute(minute);

			int second = date.getSeconds();
			exDate.setSecond(second);

			Instant instant = date.toInstant();
			ExInstant exInstant = instantParser.parse(instant);
			exDate.setInstant(exInstant);
		}
		return exDate;
	}

}
