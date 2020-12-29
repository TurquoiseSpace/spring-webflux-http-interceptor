package open.source.exchange.parser;

import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExDate;
import open.source.exchange.model.ExPrincipal;
import open.source.exchange.model.ExPublicKey;
import open.source.exchange.model.ExSslInfo;
import open.source.exchange.model.ExX500Principal;
import open.source.exchange.model.ExX509Certificate;

@Log4j2
@Service
@RequiredArgsConstructor
public class X509CertificateParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private PrincipalParser principalParser;

	@Autowired
	private X500PrincipalParser x500PrincipalParser;

	@Autowired
	private DateParser dateParser;

	@Autowired
	private PublicKeyParser publicKeyParser;

	private Collection<List<Object>> normalize(Collection<List<?>> collection) {

		log.info("normalize -> (collection) {}", collection);
		Collection<List<Object>> normalized = null;

		if (null != collection && !collection.isEmpty()) {
			List<List<Object>> temporaryOuter = new ArrayList<>();
			for (List<?> list : collection) {
				if (null != list) {
					List<Object> temporaryInner = new ArrayList<>();
					for (Object object : list) {
						if (null != object) {
							temporaryInner.add(object);
						}
					}
					temporaryOuter.add(temporaryInner);
				}
			}
			normalized = Collections.unmodifiableCollection(temporaryOuter);
		}
		return normalized;
	}

	public ExX509Certificate parse(X509Certificate x509Certificate) {

		log.info("parse -> (x509Certificate) {}", x509Certificate);
		ExX509Certificate exX509Certificate = null;

		if (null != x509Certificate) {
			ExBase exBase = objectParser.parse(x509Certificate);
			exX509Certificate = new ExX509Certificate(exBase);

			int basicConstraints = x509Certificate.getBasicConstraints();
			exX509Certificate.setBasicConstraints(basicConstraints);

			Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
			exX509Certificate.setCriticalExtensionOIDs(criticalExtensionOIDs);

			try {
				byte[] encoded = x509Certificate.getEncoded();
				exX509Certificate.setEncoded(encoded);
			} catch (CertificateEncodingException e) {
				log.error("error identifying encoded", e);
			}

			try {
				List<String> extendedKeyUsage = x509Certificate.getExtendedKeyUsage();
				exX509Certificate.setExtendedKeyUsage(extendedKeyUsage);
			} catch (CertificateParsingException e) {
				log.error("error identifying extendedKeyUsage", e);
			}

			try {
				// ? represents Object, which can be Integer followed by byte[] or String
				Collection<List<?>> issuerAlternativeNames = x509Certificate.getIssuerAlternativeNames();
				Collection<List<Object>> issuerAlternativeNamesNormalize = normalize(issuerAlternativeNames);
				exX509Certificate.setIssuerAlternativeNames(issuerAlternativeNamesNormalize);
			} catch (CertificateParsingException e) {
				log.error("error identifying issuerAlternativeNames", e);
			}

			Principal issuerDN = x509Certificate.getIssuerDN();
			ExPrincipal exIssuerDN = principalParser.parse(issuerDN);
			exX509Certificate.setIssuerDN(exIssuerDN);

			boolean[] issuerUniqueID = x509Certificate.getIssuerUniqueID();
			exX509Certificate.setIssuerUniqueID(issuerUniqueID);

			X500Principal issuerX500Principal = x509Certificate.getIssuerX500Principal();
			ExX500Principal exIssuerX500Principal = x500PrincipalParser.parse(issuerX500Principal);
			exX509Certificate.setIssuerX500Principal(exIssuerX500Principal);

			boolean[] keyUsage = x509Certificate.getKeyUsage();
			exX509Certificate.setKeyUsage(keyUsage);

			Set<String> nonCriticalExtensionOIDs = x509Certificate.getNonCriticalExtensionOIDs();
			exX509Certificate.setNonCriticalExtensionOIDs(nonCriticalExtensionOIDs);

			Date notAfter = x509Certificate.getNotAfter();
			ExDate exNotAfter = dateParser.parse(notAfter);
			exX509Certificate.setNotAfter(exNotAfter);

			Date notBefore = x509Certificate.getNotBefore();
			ExDate exNotBefore = dateParser.parse(notBefore);
			exX509Certificate.setNotBefore(exNotBefore);

			PublicKey publicKey = x509Certificate.getPublicKey();
			ExPublicKey exPublicKey = publicKeyParser.parse(publicKey);
			exX509Certificate.setPublicKey(exPublicKey);

			BigInteger serialNumber = x509Certificate.getSerialNumber();
			exX509Certificate.setSerialNumber(serialNumber);

			String signatureAlgorithmName = x509Certificate.getSigAlgName();
			exX509Certificate.setSignatureAlgorithmName(signatureAlgorithmName);

			String signatureAlgorithmOID = x509Certificate.getSigAlgOID();
			exX509Certificate.setSignatureAlgorithmOID(signatureAlgorithmOID);

			byte[] signatureAlgorithmParams = x509Certificate.getSigAlgParams();
			exX509Certificate.setSignatureAlgorithmParams(signatureAlgorithmParams);

			byte[] signature = x509Certificate.getSignature();
			exX509Certificate.setSignature(signature);

			try {
				// ? represents Object, which can be Integer followed by byte[] or String
				Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
				Collection<List<Object>> subjectAlternativeNamesNormalize = normalize(subjectAlternativeNames);
				exX509Certificate.setSubjectAlternativeNames(subjectAlternativeNamesNormalize);
			} catch (CertificateParsingException e) {
				log.error("error identifying subjectAlternativeNames", e);
			}

			Principal subjectDN = x509Certificate.getSubjectDN();
			ExPrincipal exSubjectDN = principalParser.parse(subjectDN);
			exX509Certificate.setSubjectDN(exSubjectDN);

			boolean[] subjectUniqueID = x509Certificate.getSubjectUniqueID();
			exX509Certificate.setSubjectUniqueID(subjectUniqueID);

			X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
			ExX500Principal exSubjectX500Principal = x500PrincipalParser.parse(subjectX500Principal);
			exX509Certificate.setSubjectX500Principal(exSubjectX500Principal);

			try {
				byte[] tbsCertificate = x509Certificate.getTBSCertificate();
				exX509Certificate.setTbsCertificate(tbsCertificate);
			} catch (CertificateEncodingException e) {
				log.error("error identifying tbsCertificate", e);
			}

			String type = x509Certificate.getType();
			exX509Certificate.setType(type);

			int version = x509Certificate.getVersion();
			exX509Certificate.setVersion(version);

			boolean hasUnsupportedCriticalExtension = x509Certificate.hasUnsupportedCriticalExtension();
			exX509Certificate.setHasUnsupportedCriticalExtension(hasUnsupportedCriticalExtension);

			try {
				x509Certificate.checkValidity();
			} catch (CertificateNotYetValidException certificateNotYetValidException) {
				exX509Certificate.setNotYetValidFlag(true);
				log.error("certificate not yet valid error", certificateNotYetValidException);
			} catch (CertificateExpiredException certificateExpiredException) {
				exX509Certificate.setExpiredFlag(true);
				log.error("certificate expired error", certificateExpiredException);
			}
		}
		return exX509Certificate;
	}

}
