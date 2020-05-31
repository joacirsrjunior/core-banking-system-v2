package br.com.bank.core.services.implementation;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.ETransactionType;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import br.com.bank.core.repository.TransactionRepository;
import br.com.bank.core.services.ITransactionService;
import br.com.bank.core.validations.TransactionValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionService implements ITransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private TransactionRepository transactionRepository;

    private TransactionValidation transactionValidation;

    private AccountService accountService;

    @Autowired
    public TransactionService( TransactionRepository transactionRepository,
                               TransactionValidation transactionValidation,
                               AccountService accountService ) {
        this.transactionRepository = transactionRepository;
        this.transactionValidation = transactionValidation;
        this.accountService = accountService;
    }

    @Override
    public Mono<Transaction> executeTransaction(Transaction transaction) {

        logger.debug("Processing a new transaction : {}", transaction.toString());

        return transactionValidation.validate(transaction)
                .flatMap(t -> {
                    logger.debug("Verifing if account exists...");
                    return accountService.verifyAccountExistence(t.getAccount());
                })
                .flatMap(ac -> {
                    transaction.setAccount(ac);
                    logger.debug("Account exists. Updating transaction information...");
                    return Mono.just(transaction);
                })
                .flatMap(transactionRepository::save)
                .flatMap(t -> {
                    logger.debug("Changing the account balance...");
                    if(t.getTransactionType().equals(ETransactionType.CREDIT)){
                        return this.sensibilizeAccountWithCreditOperation(t);
                    } else {
                        return this.sensibilizeAccountWithDebitOperation(t);
                    }
                })
                .onErrorResume(error -> {
                    CoreException ce = (CoreException) error;
                    logger.error("[ERROR] Executing transaction : " + ce.getErrorResponse().getError().getMsg());
                    return Mono.error(ce);
                });

    }

    private Mono<Transaction> sensibilizeAccountWithCreditOperation(Transaction transaction){
        logger.debug("Credit transaction...");
        return sensibilizeAccountBalance(transaction);
    }

    private Mono<Transaction> sensibilizeAccountWithDebitOperation(Transaction transaction){
        logger.debug("Debit transaction...");
        return this.verifyEnoughtMoneyForDebitTransaction(transaction)
                .flatMap(this::sensibilizeAccountBalance);
    }

    private Mono<Transaction> sensibilizeAccountBalance(Transaction transaction){
        logger.debug("Changing account balance. Branch {} - Account {}",
                transaction.getAccount().getBranchNumber(), transaction.getAccount().getAccountNumber());
        Account affectedAccount = transaction.getAccount();
        if(transaction.getTransactionType().equals(ETransactionType.CREDIT)){
            affectedAccount.setBalance(affectedAccount.getBalance().add(transaction.getAmount()));
        } else {
            affectedAccount.setBalance(affectedAccount.getBalance().subtract(transaction.getAmount()));
        }
        logger.debug("Saving new account balance...");
        Mono<Account> resultAccount = accountService.save(affectedAccount);
        return resultAccount
                .flatMap(ac -> {
                        transaction.setAccount(ac);
                        logger.debug("Account balance changed with success.");
                        return Mono.just(transaction);
                });
    }

    private Mono<Transaction> verifyEnoughtMoneyForDebitTransaction(Transaction transaction){
        if(transaction.getAccount().getBalance().compareTo(transaction.getAmount()) >= 0){
            return Mono.just(transaction);
        }
        logger.debug("Transaction not efetivated. Insufficient funds.");
        throw new CoreException(
                        new ApiErrorResponse(EValidationResponse.TRANSACTION_NOT_EFETIVATED_INSUFFICIENT_FUNDS));
    }

}
