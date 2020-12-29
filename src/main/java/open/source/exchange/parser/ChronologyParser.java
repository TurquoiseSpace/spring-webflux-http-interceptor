package open.source.exchange.parser;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExChronology;
import open.source.exchange.model.ExEra;
import open.source.exchange.model.ExMediaType;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChronologyParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private EraParser eraParser;

	public ExChronology parse(Chronology chronology) {

		log.info("parse -> (chronology) {}", chronology);
		ExChronology exChronology = null;

		if (null != chronology) {
			ExBase exBase = objectParser.parse(chronology);
			exChronology = new ExChronology(exBase);

			ChronoLocalDate dateNow = chronology.dateNow();
			// TODO : parse and build object
			exChronology.setDateNow(dateNow);

			List<Era> eras = chronology.eras();
			if (null != eras && !eras.isEmpty()) {
				List<ExEra> exList = new ArrayList<ExEra>();
				for (Era era : eras) {
					ExEra exEra = eraParser.parse(era);
					exList.add(exEra);
				}
				exChronology.setEras(exList);
			}

			String calendarType = chronology.getCalendarType();
			exChronology.setCalendarType(calendarType);

			String id = chronology.getId();
			exChronology.setId(id);
		}
		return exChronology;
	}

}
