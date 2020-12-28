package open.source.parser;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExBase;
import open.source.model.ExPrincipal;
import open.source.model.ExX509Certificate;

@Log4j2
@Service
@RequiredArgsConstructor
public class PrincipalParser {

	@Autowired
	private ObjectParser objectParser;

	public ExPrincipal parse(Principal principal) {

		log.info("parse -> (principal) {}", principal);
		ExPrincipal exPrincipal = null;

		if (null != principal) {
			ExBase exBase = objectParser.parse(principal);
			exPrincipal = new ExPrincipal(exBase);

			String name = principal.getName();
			exPrincipal.setName(name);
		}
		return exPrincipal;
	}

}
