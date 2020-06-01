package br.com.bank.core.repository;

import br.com.bank.core.entity.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public AccountRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<AccountEntity> insert(AccountEntity account) {
        logger.debug("Inserting new account...");
        return reactiveMongoTemplate.insert(account);
    }

    public Mono<AccountEntity> save(AccountEntity account) {
        logger.debug("Saving account...");
        return reactiveMongoTemplate.save(account);
    }

    public Mono<AccountEntity> delete(AccountEntity account) {
        logger.debug("Deleting account...");
        return reactiveMongoTemplate.remove(account)
                .flatMap(a -> Mono.just(account)).onErrorResume(error -> Mono.error(error));
    }

    public Mono<Void> deleteAll(){
        logger.debug("Deleting all accounts...");
        return reactiveMongoTemplate.dropCollection(AccountEntity.class);
    }

    public Flux<AccountEntity> findAll(){
        logger.debug("Find all accounts...");
        return reactiveMongoTemplate.findAll(AccountEntity.class);
    }

    public Mono<AccountEntity> findById(String id) {
        logger.debug("Find account by ID...");
        return reactiveMongoTemplate.findById(id, AccountEntity.class);
    }

    public Mono<AccountEntity> findByAccountDocumentNumber(AccountEntity accountFilter) {
        logger.debug("Find account by document number...");
        return reactiveMongoTemplate.findAll(AccountEntity.class)
                .filter(account -> account.getDocumentNumber()
                        .compareToIgnoreCase(accountFilter.getDocumentNumber()) == 0)
                .elementAt(0, new AccountEntity());
    }

}
