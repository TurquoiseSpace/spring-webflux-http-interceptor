package open.source.exchange.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.entity.ExApplicationContext;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEnvironment;

@Log4j2
@Service
@RequiredArgsConstructor
public class ApplicationContextParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private EnvironmentParser environmentParser;

	private ExApplicationContext parseCore(ApplicationContext applicationContext) {

		log.info("parse core -> (applicationContext) {}", applicationContext);
		ExApplicationContext exApplicationContext = null;

		if (null != applicationContext) {
			ExBase exBase = objectParser.parse(applicationContext);
			exApplicationContext = new ExApplicationContext(exBase);

			String applicationName = applicationContext.getApplicationName();
			exApplicationContext.setApplicationName(applicationName);

			int beanDefinitionCount = applicationContext.getBeanDefinitionCount();
			exApplicationContext.setBeanDefinitionCount(beanDefinitionCount);

			String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
			exApplicationContext.setBeanDefinitionNames(beanDefinitionNames);

			String displayName = applicationContext.getDisplayName();
			exApplicationContext.setDisplayName(displayName);

			Environment environment = applicationContext.getEnvironment();
			ExEnvironment exEnvironment = environmentParser.parse(environment);
			exApplicationContext.setEnvironment(exEnvironment);

			String id = applicationContext.getId();
			exApplicationContext.setId(id);

			long startupDate = applicationContext.getStartupDate();
			exApplicationContext.setStartupDate(startupDate);
		}
		return exApplicationContext;
	}

	private ExApplicationContext parseNonCore(ApplicationContext applicationContext, ExApplicationContext exApplicationContext) {

		log.info("parse non core -> (applicationContext) {} (exApplicationContext) {}", applicationContext, exApplicationContext);

		if (null != exApplicationContext) {
			ApplicationContext parent = applicationContext.getParent();
			ExApplicationContext exParent = parseCore(parent);
			exApplicationContext.setParent(exParent);
		}
		return exApplicationContext;
	}

	public ExApplicationContext parse(ApplicationContext applicationContext) {

		log.info("parse -> (applicationContext) {}", applicationContext);

		ExApplicationContext exApplicationContext = parseCore(applicationContext);
		return parseNonCore(applicationContext, exApplicationContext);
	}

}
