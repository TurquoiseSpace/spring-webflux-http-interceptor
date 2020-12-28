package open.source.parser;

import javax.security.auth.x500.X500Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExPrincipal;
import open.source.model.ExX500Principal;

@Log4j2
@Service
@RequiredArgsConstructor
public class X500PrincipalParser {

	@Autowired
	private PrincipalParser principalParser;

	public ExX500Principal parse(X500Principal x500Principal) {

		log.info("parse -> (x500Principal) {}", x500Principal);
		ExX500Principal exX500Principal = null;

		if (null != x500Principal) {
			ExPrincipal exPrincipal = principalParser.parse(x500Principal);
			exX500Principal = new ExX500Principal(exPrincipal);

			byte[] encoded = x500Principal.getEncoded();
			exX500Principal.setEncoded(encoded);
		}
		return exX500Principal;
	}

}
