package open.source.exchange.parser;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExDuration;
import open.source.exchange.model.ExInstant;
import open.source.exchange.model.ExTemporalUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class DurationParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private TemporalUnitParser temporalUnitParser;

	private ExDuration parseCore(Duration duration) {

		ExBase exBase = objectParser.parse(duration);
		ExDuration exDuration = new ExDuration(exBase);

		long seconds = duration.getSeconds();
		exDuration.setSeconds(seconds);

		int nano = duration.getNano();
		exDuration.setNano(nano);

		List<TemporalUnit> units = duration.getUnits();
		List<ExTemporalUnit> exUnits = null;
		if (null != units && !units.isEmpty()) {
			exUnits = new ArrayList<ExTemporalUnit>();
			for (TemporalUnit temporalUnit : units) {
				ExTemporalUnit exTemporalUnit = temporalUnitParser.parse(temporalUnit);
				exUnits.add(exTemporalUnit);
			}
		}
		exDuration.setUnits(exUnits);

		boolean isNegative = duration.isNegative();
		exDuration.setNegative(isNegative);

		boolean isZero = duration.isZero();
		exDuration.setZero(isZero);

		long toDays = duration.toDays();
		exDuration.setToDays(toDays);

		long toHours = duration.toHours();
		exDuration.setToHours(toHours);

		long toMinutes = duration.toMinutes();
		exDuration.setToMinutes(toMinutes);

		long toMillis = duration.toMillis();
		exDuration.setToMillis(toMillis);

		long toNanos = duration.toNanos();
		exDuration.setToNanos(toNanos);

		return exDuration;
	}

	public ExDuration parse(Duration duration) {

		log.info("parse -> (duration) {}", duration);
		ExDuration exDuration = null;

		if (null != duration) {
			exDuration = parseCore(duration);

			Duration absolute = duration.abs();
			if (null != absolute) {
				ExDuration exAbsolute = parseCore(absolute);
				exDuration.setAbsolute(exAbsolute);
			}

			Duration negated = duration.negated();
			if (null != negated) {
				ExDuration exNegated = parseCore(negated);
				exDuration.setNegated(exNegated);
			}
		}
		return exDuration;
	}

}
