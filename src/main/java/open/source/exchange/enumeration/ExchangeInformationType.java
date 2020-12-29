package open.source.exchange.enumeration;

import java.io.Serializable;

public enum ExchangeInformationType implements Serializable {

	id,
	signalType,
	events,
	applicationContext,
	attributes,
	localeContext,
	logPrefix,
	serverHttpRequest,
	serverHttpResponse,
	notModifiedFlag,
	principal,
	session,
	formData,
	multipartData;

}
