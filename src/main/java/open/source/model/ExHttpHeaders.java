package open.source.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
public class ExHttpHeaders extends ExMultiValueMap<String, String> implements Serializable {

	// https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-mapping-template-reference.html#context-variable-reference
	// https://aws.amazon.com/premiumsupport/knowledge-center/custom-headers-api-gateway-lambda/

	private List<ExMediaType> accept;

	private List<ExCharset> acceptCharset;

	private List<ExLocaleLanguageRange> acceptLanguage;

	private List<ExLocale> acceptLanguageAsLocales;

	private boolean accessControlAllowCredentials;

	private List<String> accessControlAllowHeaders;

	private List<ExHttpMethod> accessControlAllowMethods;

	private String accessControlAllowOrigin;

	private List<String> accessControlExposeHeaders;

	private long accessControlMaxAge;

	private List<String> accessControlRequestHeaders;

	private ExHttpMethod accessControlRequestMethod;

	private Set<ExHttpMethod> allow;

	private String cacheControl;

	private List<String> connection;

	private ExContentDisposition contentDisposition;

	private ExLocale contentLanguage;

	private long contentLength;

	private ExMediaType contentType;

	private long date;

	private String eTag;

	private long expires;

	private ExInetSocketAddress host;

	private List<String> ifMatch;

	private long ifModifiedSince;

	private List<String> ifNoneMatch;

	private long ifUnmodifiedSince;

	private long lastModified;

	private ExURI location;

	private String origin;

	private String pragma;

	private List<ExBase> range;

	private String upgrade;

	private List<String> vary;

	public ExHttpHeaders(ExMultiValueMap<String, String> exMultiValueMap) {

		super(exMultiValueMap);
		if (null != exMultiValueMap) {
			this.setSize(exMultiValueMap.getSize());
			this.setEmpty(exMultiValueMap.isEmpty());
			this.setMapVsList(exMultiValueMap.getMapVsList());
		}
	}

}
