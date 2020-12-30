package open.source.exchange.parser;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.SslInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExServerHttpRequest;
import open.source.exchange.model.ExSslInfo;
import open.source.exchange.model.ExX509Certificate;

@Log4j2
@Service
@RequiredArgsConstructor
public class SslInfoParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private X509CertificateParser x509CertificateParser;

	public ExSslInfo parse(SslInfo sslInfo) {

		log.trace("parse -> (sslInfo) {}", sslInfo);
		ExSslInfo exSslInfo = null;

		if (null != sslInfo) {
			ExBase exBase = objectParser.parse(sslInfo);
			exSslInfo = new ExSslInfo(exBase);

			String sessionId = sslInfo.getSessionId();
			exSslInfo.setSessionId(sessionId);

			X509Certificate[] peerCertificates = sslInfo.getPeerCertificates();
			ExX509Certificate[] exX509CertificateArray = null;
			if (null != peerCertificates && peerCertificates.length > 0) {
				exX509CertificateArray = new ExX509Certificate[peerCertificates.length];
				for (int index = 0; index < peerCertificates.length; index++) {
					X509Certificate peerCertificate = peerCertificates[index];
					ExX509Certificate exX509Certificate = x509CertificateParser.parse(peerCertificate);
					exX509CertificateArray[index] = exX509Certificate;
				}
			}
			exSslInfo.setPeerCertificates(exX509CertificateArray);
		}
		return exSslInfo;
	}

}
