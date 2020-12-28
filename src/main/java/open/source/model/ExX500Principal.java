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
public class ExX500Principal extends ExPrincipal implements Serializable {

	private byte[] encoded;

	public ExX500Principal(ExPrincipal exPrincipal) {

		super(exPrincipal);
		if (null != exPrincipal) {
			this.setName(exPrincipal.getName());
		}
	}

}
