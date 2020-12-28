package open.source.dto;

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
public class InformationExchangeDTO {

	// https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-mapping-template-reference.html#context-variable-reference
	// https://aws.amazon.com/premiumsupport/knowledge-center/custom-headers-api-gateway-lambda/

	private String requestId;

	private Long startTimestamp;

	private Long endTimestamp;

	private Long totalTime;

	private String httpMethod;

	private String apiUri;

	private String signalType;

	private String responseStatusName;

	private Integer responseStatusValue;

	private String token;

	private String accountId;

	private String clientIP;

	private String clientCountry;

	private String clientDeviceType;

	private String clientAgent;

	private String clientLanguage;

	private String clientLanguageCountry;

	private String communicationProtocol;

	private String cloudfrontForwardedProtocol;

	private String loadBalancerDNS;

	private Integer remoteRequestPort;

	// gateway ip
	private String serverIP;

	private Integer queryParameterCount;

	private String queryParameters;

	private Integer cookieCount;

	private String cookies;

	private Integer formDataCount;

	private Integer multipartDataCount;

}
