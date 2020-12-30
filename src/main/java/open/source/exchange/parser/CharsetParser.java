package open.source.exchange.parser;

import java.nio.charset.Charset;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExCharset;

@Log4j2
@Service
@RequiredArgsConstructor
public class CharsetParser {

	@Autowired
	private ObjectParser objectParser;

	public ExCharset parse(Charset charset) {

		log.trace("parse -> (charset) {}", charset);
		ExCharset exCharset = null;

		if (null != charset) {
			ExBase exBase = objectParser.parse(charset);
			exCharset = new ExCharset(exBase);

			Set<String> aliases = charset.aliases();
			exCharset.setAliases(aliases);

			boolean canEncode = charset.canEncode();
			exCharset.setCanEncode(canEncode);

			String displayName = charset.displayName();
			exCharset.setDisplayName(displayName);

			boolean isRegistered = charset.isRegistered();
			exCharset.setRegistered(isRegistered);

			String name = charset.name();
			exCharset.setName(name);
		}
		return exCharset;
	}

}
