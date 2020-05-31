package br.com.bank.core.services;

import br.com.bank.core.entity.Transaction;
import reactor.core.publisher.Mono;

public interface ITransactionService {

    Mono<Transaction> executeTransaction(Transaction transaction) throws Exception;

}
