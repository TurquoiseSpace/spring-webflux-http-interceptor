package open.source.model;

import java.io.Serializable;
import java.util.Map;

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
public class ExMediaType extends ExBase implements Serializable {

	private ExCharset charset;

	private Map<String, String> parameters;

	private double qualityValue;

	private String type;

	private String subtype;

	private boolean isConcrete;

	private boolean isWildcardType;

	private boolean isWildcardSubtype;

	private ExMediaType removeQualityValue;

	public ExMediaType(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
