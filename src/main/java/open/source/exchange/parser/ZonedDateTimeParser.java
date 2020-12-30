package open.source.exchange.parser;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.Chronology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExChronology;
import open.source.exchange.model.ExInstant;
import open.source.exchange.model.ExZonedDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class ZonedDateTimeParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private ChronologyParser chronologyParser;

	@Autowired
	private InstantParser instantParser;

	private ExZonedDateTime parseCore(ZonedDateTime zonedDateTime) {

		log.trace("parse core -> (zonedDateTime) {}", zonedDateTime);
		ExZonedDateTime exZonedDateTime = null;

		if (null != zonedDateTime) {
			ExBase exBase = objectParser.parse(zonedDateTime);
			exZonedDateTime = new ExZonedDateTime(exBase);

			Chronology chronology = zonedDateTime.getChronology();
			ExChronology exChronology = chronologyParser.parse(chronology);
			exZonedDateTime.setChronology(exChronology);

			int dayOfMonth = zonedDateTime.getDayOfMonth();
			exZonedDateTime.setDayOfMonth(dayOfMonth);

			DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
			// TODO : parse and build object
			exZonedDateTime.setDayOfWeek(dayOfWeek);

			int dayOfYear = zonedDateTime.getDayOfYear();
			exZonedDateTime.setDayOfYear(dayOfYear);

			int hour = zonedDateTime.getHour();
			exZonedDateTime.setHour(hour);

			int minute = zonedDateTime.getMinute();
			exZonedDateTime.setMinute(minute);

			Month month = zonedDateTime.getMonth();
			// TODO : parse and build object
			exZonedDateTime.setMonth(month);

			int monthValue = zonedDateTime.getMonthValue();
			exZonedDateTime.setMonthValue(monthValue);

			int nano = zonedDateTime.getNano();
			exZonedDateTime.setNano(nano);

			ZoneOffset offset = zonedDateTime.getOffset();
			// TODO : parse and build object
			exZonedDateTime.setOffset(offset);

			int second = zonedDateTime.getSecond();
			exZonedDateTime.setSecond(second);

			int year = zonedDateTime.getYear();
			exZonedDateTime.setYear(year);

			ZoneId zone = zonedDateTime.getZone();
			// TODO : parse and build object
			exZonedDateTime.setZone(zone);

			long toEpochSecond = zonedDateTime.toEpochSecond();
			exZonedDateTime.setToEpochSecond(toEpochSecond);

			Instant toInstant = zonedDateTime.toInstant();
			ExInstant exInstant = instantParser.parse(toInstant);
			exZonedDateTime.setToInstant(exInstant);

			LocalDate toLocalDate = zonedDateTime.toLocalDate();
			// TODO : parse and build object
			exZonedDateTime.setToLocalDate(toLocalDate);

			LocalDateTime toLocalDateTime = zonedDateTime.toLocalDateTime();
			// TODO : parse and build object
			exZonedDateTime.setToLocalDateTime(toLocalDateTime);

			LocalTime toLocalTime = zonedDateTime.toLocalTime();
			// TODO : parse and build object
			exZonedDateTime.setToLocalTime(toLocalTime);

			OffsetDateTime toOffsetDateTime = zonedDateTime.toOffsetDateTime();
			// TODO : parse and build object
			exZonedDateTime.setToOffsetDateTime(toOffsetDateTime);
		}
		return exZonedDateTime;
	}

	private ExZonedDateTime parseNonCore(ZonedDateTime zonedDateTime, ExZonedDateTime exZonedDateTime) {

		log.trace("parse non core -> (zonedDateTime) {}", zonedDateTime);

		if (null != exZonedDateTime) {
			ZonedDateTime withEarlierOffsetAtOverlap = zonedDateTime.withEarlierOffsetAtOverlap();
			ExZonedDateTime exWithEarlierOffsetAtOverlap = parseCore(withEarlierOffsetAtOverlap);
			exZonedDateTime.setWithEarlierOffsetAtOverlap(exWithEarlierOffsetAtOverlap);

			ZonedDateTime withFixedOffsetZone = zonedDateTime.withFixedOffsetZone();
			ExZonedDateTime exWithFixedOffsetZone = parseCore(withFixedOffsetZone);
			exZonedDateTime.setWithFixedOffsetZone(exWithFixedOffsetZone);

			ZonedDateTime withLaterOffsetAtOverlap = zonedDateTime.withLaterOffsetAtOverlap();
			ExZonedDateTime exWithLaterOffsetAtOverlap = parseCore(withLaterOffsetAtOverlap);
			exZonedDateTime.setWithLaterOffsetAtOverlap(exWithLaterOffsetAtOverlap);
		}
		return exZonedDateTime;
	}

	public ExZonedDateTime parse(ZonedDateTime zonedDateTime) {

		log.trace("parse -> (zonedDateTime) {}", zonedDateTime);

		ExZonedDateTime exZonedDateTime = parseCore(zonedDateTime);
		return parseNonCore(zonedDateTime, exZonedDateTime);
	}

}
