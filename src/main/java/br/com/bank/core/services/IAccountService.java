package br.com.bank.core.services;

import br.com.bank.core.api.dto.AccountDTO;
import br.com.bank.core.entity.AccountEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {

    Flux<AccountEntity> findAll();
    Mono<AccountEntity> findById(String id);
    Mono<AccountEntity> create(AccountDTO accountDTO);
    Mono<AccountEntity> save(AccountEntity account);
    Mono<AccountEntity> delete(AccountEntity account);

}
