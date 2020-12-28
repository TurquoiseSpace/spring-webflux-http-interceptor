package open.source.repository.asynchronous;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import open.source.entity.InformationExchange;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class InformationExchangeRepoAsyncImpl implements InformationExchangeRepoAsyncCustom {

	private final ReactiveMongoTemplate reactiveMongoTemplate;

	public <T> Mono<Boolean> updateAttribute(String identifier, String attributeName, T attributeValue) {

		log.info("update attribute -> (identifier) {} (attributeName) {} (attributeValue) {}", identifier, attributeName, attributeValue);
		if (null != attributeValue) {
			Query query = Query.query(Criteria.where("id").is(identifier));
			Update update = new Update().set(attributeName, attributeValue);
			return reactiveMongoTemplate
				.updateFirst(query, update, InformationExchange.class)
				.flatMap(updateResult -> {
					long matchedCount = updateResult.getMatchedCount();
					boolean wasAcknowledged = updateResult.wasAcknowledged();
					boolean isModifiedCountAvailable = updateResult.isModifiedCountAvailable();
					long modifiedCount = updateResult.getModifiedCount();
					log.info("update result -> (identifier) {} (attributeName) {} (matchedCount) {} (wasAcknowledged) {} (isModifiedCountAvailable) {} (modifiedCount) {}",
							identifier, attributeName, matchedCount, wasAcknowledged, isModifiedCountAvailable, modifiedCount);
					return Mono.just(true);
				});
		} else {
			return Mono.just(false);
		}
	}

}
