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
public class ExHttpStatus extends ExEnum implements Serializable {

	private int value;

	private String reasonPhrase;

	private boolean is1xxInformational;

	private boolean is2xxSuccessful;

	private boolean is3xxRedirection;

	private boolean is4xxClientError;

	private boolean is5xxServerError;

	private boolean isError;

	private ExSeries series;

	public ExHttpStatus(ExEnum exEnum) {

		super(exEnum);
		if (null != exEnum) {
			this.setDeclaringClass(exEnum.getDeclaringClass());
			this.setName(exEnum.getName());
			this.setOrdinal(exEnum.getOrdinal());
		}
	}

}
