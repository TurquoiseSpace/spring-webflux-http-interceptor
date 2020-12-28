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
public class ExRequestPath extends ExPathContainer implements Serializable {

	private ExPathContainer contextPath;

	private ExPathContainer pathWithinApplication;

	public ExRequestPath(ExPathContainer exPathContainer) {

		super(exPathContainer);
		if (null != exPathContainer) {
			this.setElements(exPathContainer.getElements());
			this.setValue(exPathContainer.getValue());
		}
	}

}
