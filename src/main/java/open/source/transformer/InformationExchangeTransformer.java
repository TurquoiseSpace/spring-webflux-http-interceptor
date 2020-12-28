package open.source.transformer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.dto.InformationExchangeDTO;
import open.source.entity.InformationExchange;
import open.source.enumeration.TimeEvent;
import open.source.model.ExEnum;
import open.source.model.ExHttpCookie;
import open.source.model.ExHttpHeaders;
import open.source.model.ExHttpStatus;
import open.source.model.ExInetSocketAddress;
import open.source.model.ExLocale;
import open.source.model.ExLocaleContext;
import open.source.model.ExMultiValueMap;
import open.source.model.ExPart;
import open.source.model.ExRequestPath;
import open.source.model.ExServerHttpRequest;
import open.source.model.ExServerHttpResponse;
import open.source.model.ExURI;
import open.source.model.Time;

@Log4j2
@Service
@RequiredArgsConstructor
public class InformationExchangeTransformer {

	private static final String TOKEN = "token";
	private static final String ACCOUNT_ID = "accountId";
	private static final String ACCOUNT_ID_VARIANT = "AccountId";
	private static final String SOURCE_IP = "sourceIP";
	private static final String CLOUDFRONT_VIEWER_COUNTRY = "CloudFront-Viewer-Country";
	private static final String USER_AGENT = "User-Agent";
	private static final String CLOUDFRONT_IS_DESKTOP_VIEWER = "CloudFront-Is-Desktop-Viewer";
	private static final String CLOUDFRONT_IS_MOBILE_VIEWER = "CloudFront-Is-Mobile-Viewer";
	private static final String CLOUDFRONT_IS_SMART_TV_VIEWER = "CloudFront-Is-SmartTV-Viewer";
	private static final String CLOUDFRONT_IS_TABLET_VIEWER = "CloudFront-Is-Tablet-Viewer";
	private static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
	private static final String CLOUDFRONT_FORWARDED_PROTO = "CloudFront-Forwarded-Proto";
	private static final String X_FORWARDED_FOR = "X-Forwarded-For";

	private enum ClientDeviceType {

		Desktop,
		Mobile,
		SmartTV,
		Tablet;
	}

	// https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-mapping-template-reference.html#context-variable-reference
	// https://aws.amazon.com/premiumsupport/knowledge-center/custom-headers-api-gateway-lambda/

	// TODO : output the below information
		// events.Begin.value
		// events.End.value
		// serverHttpRequest._id
		// serverHttpRequest.methodValue
		// serverHttpRequest.path.value
		// events.Frame.value
		// signalType.name
		// serverHttpResponse.statusCode.value
		// serverHttpResponse.statusCode.name
		// serverHttpRequest.headers.toString.token
		// serverHttpRequest.headers.toString.accountId
		// serverHttpRequest.headers.toString.AccountId
		// serverHttpRequest.headers.toString.sourceIP								->		Client IP
		// serverHttpRequest.headers.toString.CloudFront-Viewer-Country
		// serverHttpRequest.headers.toString.User-Agent
		// serverHttpRequest.headers.toString.CloudFront-Is-Desktop-Viewer
		// serverHttpRequest.headers.toString.CloudFront-Is-Mobile-Viewer
		// serverHttpRequest.headers.toString.CloudFront-Is-SmartTV-Viewer
		// serverHttpRequest.headers.toString.CloudFront-Is-Tablet-Viewer
		// localeContext.locale.displayLanguage
		// localeContext.locale.displayCountry
		// serverHttpRequest.headers.toString.X-Forwarded-Proto
		// serverHttpRequest.headers.toString.CloudFront-Forwarded-Proto
		// serverHttpRequest.uri.authority
		// serverHttpRequest.headers.toString.X-Forwarded-For						->		Server IP
		// serverHttpRequest.remoteAddress.port
		// serverHttpRequest.queryParams.size
		// serverHttpRequest.queryParams.toString
		// serverHttpRequest.cookies.size
		// serverHttpRequest.cookies.toString
		// formData.size
		// multipartData.size

	public InformationExchangeDTO transform(InformationExchange informationExchange) {

		log.info("transform -> (informationExchange) {}", informationExchange);
		InformationExchangeDTO informationExchangeDTO = new InformationExchangeDTO();

		if (null != informationExchange) {

			Map<TimeEvent, Time<Long>> events = informationExchange.getEvents();
			if (null != events) {
				Time<Long> begin = events.get(TimeEvent.Begin);
				if (null != begin) {
					Long startTimestamp = begin.getValue();
					informationExchangeDTO.setStartTimestamp(startTimestamp);
				}

				Time<Long> end = events.get(TimeEvent.End);
				if (null != end) {
					Long endTimestamp = end.getValue();
					informationExchangeDTO.setEndTimestamp(endTimestamp);
				}

				Time<Long> frame = events.get(TimeEvent.Frame);
				if (null != frame) {
					Long totalTimeNanoSeconds = frame.getValue();
					informationExchangeDTO.setTotalTime(totalTimeNanoSeconds);
				}
			}

			ExServerHttpRequest serverHttpRequest = informationExchange.getServerHttpRequest();
			if (null != serverHttpRequest) {
				informationExchangeDTO.setRequestId(serverHttpRequest.getId());

				informationExchangeDTO.setHttpMethod(serverHttpRequest.getMethodValue());

				ExRequestPath path = serverHttpRequest.getPath();
				if (null != path) {
					informationExchangeDTO.setApiUri(path.getValue());
				}

				ExHttpHeaders headers = serverHttpRequest.getHeaders();
				if (null != headers) {
					Map<String, List<String>> headerMap = headers.getMapVsList();
					if (null != headerMap) {
						List<String> token = headerMap.get(TOKEN);
						if (null != token && !token.isEmpty()) {
							informationExchangeDTO.setToken(token.get(0));
						}

						List<String> accountId = headerMap.get(ACCOUNT_ID);
						if (null != accountId && !accountId.isEmpty()) {
							informationExchangeDTO.setAccountId(accountId.get(0));
						}

						List<String> AccountId = headerMap.get(ACCOUNT_ID_VARIANT);
						if (null != AccountId && !AccountId.isEmpty()) {
							informationExchangeDTO.setAccountId(AccountId.get(0));
						}

						List<String> sourceIp = headerMap.get(SOURCE_IP);
						if (null != sourceIp && !sourceIp.isEmpty()) {
							informationExchangeDTO.setClientIP(sourceIp.get(0));
						}

						List<String> cloudfrontViewerCountry = headerMap.get(CLOUDFRONT_VIEWER_COUNTRY);
						if (null != cloudfrontViewerCountry && !cloudfrontViewerCountry.isEmpty()) {
							informationExchangeDTO.setClientCountry(cloudfrontViewerCountry.get(0));
						}

						List<String> userAgent = headerMap.get(USER_AGENT);
						if (null != userAgent && !userAgent.isEmpty()) {
							informationExchangeDTO.setClientAgent(userAgent.get(0));
						}

						List<String> desktopViewer = headerMap.get(CLOUDFRONT_IS_DESKTOP_VIEWER);
						if (null != desktopViewer && !desktopViewer.isEmpty() && Boolean.parseBoolean(desktopViewer.get(0))) {
							informationExchangeDTO.setClientDeviceType(ClientDeviceType.Desktop.name());
						}

						List<String> mobileViewer = headerMap.get(CLOUDFRONT_IS_MOBILE_VIEWER);
						if (null != mobileViewer && !mobileViewer.isEmpty() && Boolean.parseBoolean(mobileViewer.get(0))) {
							informationExchangeDTO.setClientDeviceType(ClientDeviceType.Mobile.name());
						}

						List<String> smartTvViewer = headerMap.get(CLOUDFRONT_IS_SMART_TV_VIEWER);
						if (null != smartTvViewer && !smartTvViewer.isEmpty() && Boolean.parseBoolean(smartTvViewer.get(0))) {
							informationExchangeDTO.setClientDeviceType(ClientDeviceType.SmartTV.name());
						}

						List<String> tabletViewer = headerMap.get(CLOUDFRONT_IS_TABLET_VIEWER);
						if (null != tabletViewer && !tabletViewer.isEmpty() && Boolean.parseBoolean(tabletViewer.get(0))) {
							informationExchangeDTO.setClientDeviceType(ClientDeviceType.Tablet.name());
						}

						List<String> xForwardedProto = headerMap.get(X_FORWARDED_PROTO);
						if (null != xForwardedProto && !xForwardedProto.isEmpty()) {
							informationExchangeDTO.setCommunicationProtocol(xForwardedProto.get(0));
						}

						List<String> cloudfrontForwardedProto = headerMap.get(CLOUDFRONT_FORWARDED_PROTO);
						if (null != cloudfrontForwardedProto && !cloudfrontForwardedProto.isEmpty()) {
							informationExchangeDTO.setCloudfrontForwardedProtocol(cloudfrontForwardedProto.get(0));
						}

						List<String> xForwardedFor = headerMap.get(X_FORWARDED_FOR);
						if (null != xForwardedFor && !xForwardedFor.isEmpty()) {
							informationExchangeDTO.setServerIP(xForwardedFor.get(0));
						}
					}
				}

				ExURI uri = serverHttpRequest.getUri();
				if (null != uri) {
					informationExchangeDTO.setLoadBalancerDNS(uri.getAuthority());
				}

				ExInetSocketAddress remoteAddress = serverHttpRequest.getRemoteAddress();
				if (null != remoteAddress) {
					informationExchangeDTO.setRemoteRequestPort(remoteAddress.getPort());
				}

				ExMultiValueMap<String, String> queryParams = serverHttpRequest.getQueryParams();
				if (null != queryParams) {
					informationExchangeDTO.setQueryParameterCount(queryParams.getSize());

					informationExchangeDTO.setQueryParameters(queryParams.getToString());
				}

				ExMultiValueMap<String, ExHttpCookie> cookies = serverHttpRequest.getCookies();
				if (null != cookies) {
					informationExchangeDTO.setCookieCount(cookies.getSize());

					informationExchangeDTO.setCookies(cookies.getToString());
				}
			}

			ExEnum signalType = informationExchange.getSignalType();
			if (null != signalType) {
				informationExchangeDTO.setSignalType(signalType.getName());
			}

			ExServerHttpResponse serverHttpResponse = informationExchange.getServerHttpResponse();
			if (null != serverHttpResponse) {
				ExHttpStatus statusCode = serverHttpResponse.getStatusCode();
				if (null != statusCode) {
					informationExchangeDTO.setResponseStatusValue(statusCode.getValue());

					informationExchangeDTO.setResponseStatusName(statusCode.getName());
				}
			}

			ExLocaleContext localeContext = informationExchange.getLocaleContext();
			if (null != localeContext) {
				ExLocale locale = localeContext.getLocale();
				if (null != locale) {
					informationExchangeDTO.setClientLanguage(locale.getDisplayLanguage());

					informationExchangeDTO.setClientLanguageCountry(locale.getDisplayCountry());
				}
			}

			ExMultiValueMap<String, String> formData = informationExchange.getFormData();
			if (null != formData) {
				informationExchangeDTO.setFormDataCount(formData.getSize());
			}

			ExMultiValueMap<String, ExPart> multipartData = informationExchange.getMultipartData();
			if (null != multipartData) {
				informationExchangeDTO.setMultipartDataCount(multipartData.getSize());
			}
		}

		return informationExchangeDTO;
	}

}
