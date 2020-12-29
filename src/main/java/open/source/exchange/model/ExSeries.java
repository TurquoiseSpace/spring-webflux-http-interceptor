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
public class ExSeries extends ExEnum implements Serializable {

	private int value;

	public ExSeries(ExEnum exEnum) {

		super(exEnum);
		if (null != exEnum) {
			this.setDeclaringClass(exEnum.getDeclaringClass());
			this.setName(exEnum.getName());
			this.setOrdinal(exEnum.getOrdinal());
		}
	}

}
