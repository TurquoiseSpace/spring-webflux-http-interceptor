package open.source.exchange.model;

import java.io.Serializable;

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
public class ExElement extends ExBase implements Serializable {

	private String value;

	public ExElement(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
