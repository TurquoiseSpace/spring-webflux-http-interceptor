package open.source.exchange.parser;

import java.time.chrono.Era;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEra;

@Log4j2
@Service
@RequiredArgsConstructor
public class EraParser {

	@Autowired
	private ObjectParser objectParser;

	public ExEra parse(Era era) {

		log.info("parse -> (era) {}", era);
		ExEra exEra = null;

		if (null != era) {
			ExBase exBase = objectParser.parse(era);
			exEra = new ExEra(exBase);

			int value = era.getValue();
			exEra.setValue(value);
		}
		return exEra;
	}

}
