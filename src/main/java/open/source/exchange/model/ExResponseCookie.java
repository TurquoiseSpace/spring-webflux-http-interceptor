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
public class ExResponseCookie extends ExHttpCookie implements Serializable {

	private String domain;

	private ExDuration maxAge;

	private String path;

	private String sameSite;

	private boolean httpOnlyFlag;

	private boolean secureFlag;

	public ExResponseCookie(ExHttpCookie exHttpCookie) {

		super(exHttpCookie);
		if (null != exHttpCookie) {
			this.setName(exHttpCookie.getName());
			this.setValue(exHttpCookie.getValue());
		}
	}

}
