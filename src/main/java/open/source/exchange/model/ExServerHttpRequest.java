package open.source.exchange.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;

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
public class ExServerHttpRequest extends ExBase implements Serializable {

	private String body;

	private ExMultiValueMap<String, ExHttpCookie> cookies;

	private ExHttpHeaders headers;

	@Indexed(background = true)
	private String id;

	private ExHttpMethod method;

	private String methodValue;

	private ExRequestPath path;

	private ExMultiValueMap<String, String> queryParams;

	private ExInetSocketAddress remoteAddress;

	private ExSslInfo sslInfo;

	private ExURI uri;

	public ExServerHttpRequest(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
