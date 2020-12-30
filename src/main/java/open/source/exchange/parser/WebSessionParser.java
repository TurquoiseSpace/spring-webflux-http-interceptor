package open.source.exchange.parser;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.WebSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExDuration;
import open.source.exchange.model.ExInstant;
import open.source.exchange.model.ExWebSession;

@Log4j2
@Service
@RequiredArgsConstructor
public class WebSessionParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private InstantParser instantParser;

	@Autowired
	private DurationParser durationParser;

	public ExWebSession parse(WebSession webSession) {

		log.trace("parse -> (webSession) {}", webSession);
		ExWebSession exWebSession = null;

		if (null != webSession) {
			ExBase exBase = objectParser.parse(webSession);
			exWebSession = new ExWebSession(exBase);

			Map<String, Object> attributes = webSession.getAttributes();
			exWebSession.setAttributes(attributes);

			Instant creationTime = webSession.getCreationTime();
			ExInstant exCreationTime = instantParser.parse(creationTime);
			exWebSession.setCreationTime(exCreationTime);

			String id = webSession.getId();
			exWebSession.setId(id);

			Instant lastAccessTime = webSession.getLastAccessTime();
			ExInstant exLastAccessTime = instantParser.parse(lastAccessTime);
			exWebSession.setLastAccessTime(exLastAccessTime);

			Duration maxIdleTime = webSession.getMaxIdleTime();
			ExDuration exMaxIdleTime = durationParser.parse(maxIdleTime);
			exWebSession.setMaxIdleTime(exMaxIdleTime);

			boolean isExpired = webSession.isExpired();
			exWebSession.setExpired(isExpired);

			boolean isStarted = webSession.isStarted();
			exWebSession.setStarted(isStarted);
		}
		return null;
	}

}
