package open.source.exchange.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEnvironment;

@Log4j2
@Service
@RequiredArgsConstructor
public class EnvironmentParser {

	@Autowired
	private ObjectParser objectParser;

	public ExEnvironment parse(Environment environment) {

		log.trace("parse -> (environment) {}", environment);
		ExEnvironment exEnvironment = null;

		if (null != environment) {
			ExBase exBase = objectParser.parse(environment);
			exEnvironment = new ExEnvironment(exBase);

			String[] activeProfiles = environment.getActiveProfiles();
			exEnvironment.setActiveProfiles(activeProfiles);

			String[] defaultProfiles = environment.getDefaultProfiles();
			exEnvironment.setDefaultProfiles(defaultProfiles);
		}
		return exEnvironment;
	}

}
