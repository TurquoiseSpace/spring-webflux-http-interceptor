package open.source.exchange.parser;

import java.time.temporal.TemporalUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExDuration;
import open.source.exchange.model.ExTemporalUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class TemporalUnitParser {

	@Autowired
	private ObjectParser objectParser;

	public ExTemporalUnit parse(TemporalUnit temporalUnit) {

		log.debug("parse -> (temporalUnit) {}", temporalUnit);
		ExTemporalUnit exTemporalUnit = null;

		if (null != temporalUnit) {
			ExBase exBase = objectParser.parse(temporalUnit);
			exTemporalUnit = new ExTemporalUnit(exBase);

			boolean isDateBased = temporalUnit.isDateBased();
			exTemporalUnit.setDateBased(isDateBased);

			boolean isDurationEstimated = temporalUnit.isDurationEstimated();
			exTemporalUnit.setDurationEstimated(isDurationEstimated);

			boolean isTimeBased = temporalUnit.isTimeBased();
			exTemporalUnit.setTimeBased(isTimeBased);

			// Note : do not parse below, cyclic dependency, infinite recursive loop
			// temporalUnit.getDuration();
		}
		return exTemporalUnit;
	}

}
