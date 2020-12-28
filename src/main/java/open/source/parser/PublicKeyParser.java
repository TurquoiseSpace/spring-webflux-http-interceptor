package open.source.parser;

import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.model.ExBase;
import open.source.model.ExPublicKey;
import open.source.model.ExX509Certificate;

@Log4j2
@Service
@RequiredArgsConstructor
public class PublicKeyParser {

	@Autowired
	private ObjectParser objectParser;

	public ExPublicKey parse(PublicKey publicKey) {

		log.info("parse -> (publicKey) {}", publicKey);
		ExPublicKey exPublicKey = null;

		if (null != publicKey) {
			ExBase exBase = objectParser.parse(publicKey);
			exPublicKey = new ExPublicKey(exBase);

			String algorithm = publicKey.getAlgorithm();
			exPublicKey.setAlgorithm(algorithm);

			byte[] encoded = publicKey.getEncoded();
			exPublicKey.setEncoded(encoded);

			String format = publicKey.getFormat();
			exPublicKey.setFormat(format);
		}
		return exPublicKey;
	}

}
