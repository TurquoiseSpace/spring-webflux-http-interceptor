package open.source.exchange.parser;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExHttpHeaders;
import open.source.exchange.model.ExPart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class PartParser {

	@Autowired
	private ObjectParser objectParser;

	@Autowired
	private HttpHeadersParser httpHeadersParser;

	@Autowired
	private DataBufferListParser dataBufferListParser;

	private Mono<ExPart> parseAsyncState(Part part, ExPart exPart) {

		log.trace("parse sync state -> (part) {} (exPart) {}", part, exPart);

		Flux<DataBuffer> content = part.content();
		// TODO : parse and build object
		// TODO : fix it
		// https://stackoverflow.com/questions/45240005/how-to-log-request-and-response-bodies-in-spring-webflux
		// java.lang.IllegalStateException: Only one connection receive subscriber allowed.
		// https://blog.csdn.net/u011084148/article/details/100144727
		/*
		return content
			.collectList()
			.map(dataBufferList -> {
				String completeDataBufferString = dataBufferListParser.parse(dataBufferList);
				exPart.setContent(completeDataBufferString);
				return exPart;
			});
		*/
		//content.collectList().doOnNext(onNext);
		//content.collectList().doOnSuccess(onSuccess);
		//content.collectList().doOnSuccessOrError(onSuccessOrError);
		return Mono.just(exPart);
	}

	private ExPart parseSyncState(Part part) {

		log.trace("parse sync state -> (part) {}", part);

		ExBase exBase = objectParser.parse(part);
		ExPart exPart = new ExPart(exBase);

		String name = part.name();
		exPart.setName(name);

		HttpHeaders headers = part.headers();
		ExHttpHeaders exHttpHeaders = httpHeadersParser.parse(headers);
		exPart.setHeaders(exHttpHeaders);

		return exPart;
	}

	public Mono<ExPart> parse(Part part) {

		log.trace("parse -> (part) {}", part);
		ExPart exPart = new ExPart();

		if (null == part) {
			return Mono.just(exPart);
		} else {
			exPart = parseSyncState(part);
			return parseAsyncState(part, exPart);
		}
	}

}
