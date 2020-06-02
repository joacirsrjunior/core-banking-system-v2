package br.com.joacirjunior.corebanking.services;

import br.com.joacirjunior.corebanking.dto.TransactionDTO;
import br.com.joacirjunior.corebanking.entity.TransactionEntity;

import java.util.Optional;

public interface ITransactionService {

    Optional<TransactionEntity> executeTransaction(TransactionDTO transactionDTO);

}
