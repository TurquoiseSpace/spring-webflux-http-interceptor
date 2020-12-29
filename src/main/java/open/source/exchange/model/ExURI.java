package open.source.exchange.model;

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
public class ExURI extends ExBase implements Serializable {

	private String authority;

	private String fragment;

	private String host;

	private String path;

	private int port;

	private String query;

	private String rawAuthority;

	private String rawFragment;

	private String rawPath;

	private String rawQuery;

	private String rawSchemeSpecificPart;

	private String rawUserInfo;

	private String scheme;

	private String schemeSpecificPart;

	private String userInfo;

	private boolean absoluteFlag;

	private boolean opaqueFlag;

	private String toASCIIString;

	private ExURI normalize;

	private ExURI serverAuthority;

	private ExURL url;

	public ExURI(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
