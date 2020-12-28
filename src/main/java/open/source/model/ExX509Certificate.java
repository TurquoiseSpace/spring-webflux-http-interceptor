package open.source.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExX509Certificate extends ExBase implements Serializable {

	private int basicConstraints;

	private Set<String> criticalExtensionOIDs;

	private byte[] encoded;

	private List<String> extendedKeyUsage;

	// Object can be Integer or byte[] or String
	private Collection<List<Object>> issuerAlternativeNames;

	private ExPrincipal issuerDN;

	private boolean[] issuerUniqueID;

	private ExX500Principal issuerX500Principal;

	private boolean[] keyUsage;

	private Set<String> nonCriticalExtensionOIDs;

	private ExDate notAfter;

	private ExDate notBefore;

	private ExPublicKey publicKey;

	private BigInteger serialNumber;

	private String signatureAlgorithmName;

	private String signatureAlgorithmOID;

	private byte[] signatureAlgorithmParams;

	private byte[] signature;

	// Object can be Integer or byte[] or String
	private Collection<List<Object>> subjectAlternativeNames;

	private ExPrincipal subjectDN;

	private boolean[] subjectUniqueID;

	private ExX500Principal subjectX500Principal;

	private byte[] tbsCertificate;

	private String type;

	private int version;

	private boolean hasUnsupportedCriticalExtension;

	private Boolean notYetValidFlag;

	private Boolean expiredFlag;

	public ExX509Certificate(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
