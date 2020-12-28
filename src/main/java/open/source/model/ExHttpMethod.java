package open.source.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExHttpMethod extends ExEnum implements Serializable {

	public ExHttpMethod(ExEnum exEnum) {

		super(exEnum);
		if (null != exEnum) {
			this.setDeclaringClass(exEnum.getDeclaringClass());
			this.setName(exEnum.getName());
			this.setOrdinal(exEnum.getOrdinal());
		}
	}

}
