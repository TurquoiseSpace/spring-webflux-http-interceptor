package open.source.repository.asynchronous;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import open.source.entity.ExApplicationContext;
import reactor.core.publisher.Mono;

@Repository
public interface ExApplicationContextRepoAsync extends ReactiveMongoRepository<ExApplicationContext, String> {

	Mono<ExApplicationContext> findByDocumentId(String documentId);

}
