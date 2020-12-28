package open.source.repository.asynchronous;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import open.source.entity.InformationExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface InformationExchangeRepoAsync extends ReactiveMongoRepository<InformationExchange, String>, InformationExchangeRepoAsyncCustom {

	Mono<InformationExchange> findById(String id);

	@Query("{'events.Begin.value' : {$gte : ?0, $lt : ?1}}")
	Flux<InformationExchange> findByApiHitsBetween(long fromTimestamp, long tillTimestamp);

}
