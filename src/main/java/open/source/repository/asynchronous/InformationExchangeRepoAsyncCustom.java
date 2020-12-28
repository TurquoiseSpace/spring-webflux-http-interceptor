package open.source.repository.asynchronous;

import reactor.core.publisher.Mono;

public interface InformationExchangeRepoAsyncCustom {

	<T> Mono<Boolean> updateAttribute(String identifier, String attributeName, T attributeValue);

}
