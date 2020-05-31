package br.com.bank.core.repository;

import br.com.bank.core.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class AccountRepository {

    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public AccountRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<Account> insert(Account account) {
        return reactiveMongoTemplate.insert(account);
    }

    public Mono<Account> save(Account account) {
        return reactiveMongoTemplate.save(account);
    }

    public Mono<Account> delete(Account account) {
        return reactiveMongoTemplate.remove(account)
                .flatMap(a -> Mono.just(account)).onErrorResume(error -> Mono.error(error));
    }

    public Mono<Void> deleteAll(){
        return reactiveMongoTemplate.dropCollection(Account.class);
    }

    public Flux<Account> findAll(){
        return reactiveMongoTemplate.findAll(Account.class);
    }

    public Mono<Account> findById(String id) {
        return reactiveMongoTemplate.findById(id, Account.class);
    }

    public Flux<Account> findByBranch(Account accountFilter) {
        return reactiveMongoTemplate.findAll(Account.class)
                .filter(account -> account.getBranchNumber()
                        .compareToIgnoreCase(accountFilter.getBranchNumber()) == 0);
    }

    public Flux<Account> findByAccountNumber(Account accountFilter) {
        return reactiveMongoTemplate.findAll(Account.class)
                .filter(account -> account.getAccountNumber()
                        .compareToIgnoreCase(accountFilter.getAccountNumber()) == 0);
    }

    public Mono<Account> findByBranchAndAccountNumber(Account accountFilter) {
        Query queryBranchFilter = new Query(where("branchNumber").is(accountFilter.getBranchNumber()));
        return reactiveMongoTemplate.find(queryBranchFilter, Account.class)
                    .filter(account -> account.getAccountNumber()
                            .compareToIgnoreCase(accountFilter.getAccountNumber()) == 0)
                    .elementAt(0, new Account());
    }

}
