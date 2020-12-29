package open.source.exchange.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Information extends ExBase implements Serializable {

	// private ExApplicationContext applicationContext;

	private Map<String, Object> attributes;

	private ExLocaleContext localeContext;

	private String logPrefix;

	private ExServerHttpRequest serverHttpRequest;

	private ExServerHttpResponse serverHttpResponse;

	private boolean notModifiedFlag;

	private ExPrincipal principal;

	private ExWebSession session;

	private ExMultiValueMap<String, String> formData;

	private ExMultiValueMap<String, ExPart> multipartData;

	public Information(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
