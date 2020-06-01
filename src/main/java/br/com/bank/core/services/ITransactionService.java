package br.com.bank.core.services;

import br.com.bank.core.entity.TransactionEntity;
import reactor.core.publisher.Mono;

public interface ITransactionService {

    Mono<TransactionEntity> executeTransaction(TransactionEntity transaction) throws Exception;

}
