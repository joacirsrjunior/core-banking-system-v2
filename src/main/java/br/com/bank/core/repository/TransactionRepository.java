package br.com.bank.core.repository;

import br.com.bank.core.entity.TransactionEntity;
import br.com.bank.core.services.implementation.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TransactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public TransactionRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<TransactionEntity> insert(TransactionEntity transaction) {
        logger.debug("Inserting transaction...");
        return reactiveMongoTemplate.insert(transaction);
    }

    public Mono<TransactionEntity> save(TransactionEntity transaction) {
        logger.debug("Saving transaction into database...");
        return reactiveMongoTemplate.save(transaction);
    }

    public Mono<Void> deleteAll(){
        logger.debug("Deleting all transactions...");
        return reactiveMongoTemplate.dropCollection(TransactionEntity.class);
    }

}
