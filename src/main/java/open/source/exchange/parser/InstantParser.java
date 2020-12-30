package open.source.exchange.parser;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExDate;
import open.source.exchange.model.ExInstant;

@Log4j2
@Service
@RequiredArgsConstructor
public class InstantParser {

	@Autowired
	private ObjectParser objectParser;

	public ExInstant parse(Instant instant) {

		log.debug("parse -> (instant) {}", instant);
		ExInstant exInstant = null;

		if (null != instant) {
			ExBase exBase = objectParser.parse(instant);
			exInstant = new ExInstant(exBase);

			long epochSecond = instant.getEpochSecond();
			exInstant.setEpochSecond(epochSecond);

			int nano = instant.getNano();
			exInstant.setNano(nano);

			long toEpochMilli = instant.toEpochMilli();
			exInstant.setToEpochMilli(toEpochMilli);
		}
		return exInstant;
	}

}
