package open.source.model;

import java.io.Serializable;

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
public class ExInetAddress extends ExBase implements Serializable {

	private byte[] address;

	private String canonicalHostName;

	private String hostAddress;

	private String hostName;

	private boolean anyLocalAddressFlag;

	private boolean linkLocalAddressFlag;

	private boolean loopbackAddressFlag;

	private boolean multicastGlobalFlag;

	private boolean multicastLinkLocalFlag;

	private boolean multicastNodeLocalFlag;

	private boolean multicastOrgLocalFlag;

	private boolean multicastSiteLocalFlag;

	private boolean multicastAddressFlag;

	private boolean siteLocalAddressFlag;

	public ExInetAddress(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
