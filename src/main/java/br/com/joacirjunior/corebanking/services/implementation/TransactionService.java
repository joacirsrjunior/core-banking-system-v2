package br.com.joacirjunior.corebanking.services.implementation;

import br.com.joacirjunior.corebanking.controller.api.ApiErrorResponse;
import br.com.joacirjunior.corebanking.dto.TransactionDTO;
import br.com.joacirjunior.corebanking.dto.mapper.TransactionMapper;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import br.com.joacirjunior.corebanking.entity.TransactionEntity;
import br.com.joacirjunior.corebanking.enums.ELimitProfile;
import br.com.joacirjunior.corebanking.enums.ETransactionType;
import br.com.joacirjunior.corebanking.enums.EValidationResponse;
import br.com.joacirjunior.corebanking.exceptions.*;
import br.com.joacirjunior.corebanking.repository.AccountRepository;
import br.com.joacirjunior.corebanking.repository.TransactionRepository;
import br.com.joacirjunior.corebanking.services.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @Override
    public Optional<TransactionEntity> executeTransaction(TransactionDTO transactionDTO) {
        LOGGER.debug("Account indetification...");
        Optional<AccountEntity> optAccount = this.accountService.findById(transactionDTO.getAccountId());
        if(optAccount.isEmpty()){
            LOGGER.error("Account not found");
            throw new CoreException(new ApiErrorResponse(EValidationResponse.VALIDATION_ACCOUNT_NOT_FOUND));
        }
        LOGGER.debug("Transaction mapper...");
        TransactionEntity transaction = TransactionMapper.convertToEntity(transactionDTO, optAccount.get());
        LOGGER.debug("Verify transaction operation...");
        Optional<TransactionEntity> optTransaction = this.sensibilizeAccountBalance(transaction);
        if(optTransaction.isEmpty()){
            LOGGER.error("Error adding new transaction");
            throw new CoreException(new ApiErrorResponse(EValidationResponse.TRANSACTION_CREATE_ERROR));
        }
        return optTransaction;
    }

    public Optional<TransactionEntity> sensibilizeAccountBalance(TransactionEntity transaction) {
        LOGGER.info("sensibilizeAccountBalance");

        if(transaction.getOperationType().getTransactionType().compareTo(ETransactionType.CREDIT) == 0){
            LOGGER.info("Credit operation : {}", transaction.toString());
            return this.creditOperation(transaction);
        } else {
            LOGGER.info("Debit operation : {}", transaction.toString());
            return this.debitOperation(transaction);
        }

    }

    private Optional<TransactionEntity> debitOperation(TransactionEntity transaction) {
        AccountEntity account = transaction.getAccount();
        if(transaction.getAccount().getBalance().compareTo(transaction.getAmount().abs()) >= 0){
            LOGGER.debug("Balance positive for debit operation");
            LOGGER.debug("Updating account balance");
            account.setBalance(account.getBalance().subtract(transaction.getAmount().abs())
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
            if(account.getAvailableLimit().subtract(transaction.getAmount().abs()).compareTo(BigDecimal.ZERO) < 0){
                LOGGER.error("LIMIT INVALID FOR : {}", account.toString());
                throw new CoreException(new ApiErrorResponse(EValidationResponse.INVALID_LIMIT));
            }
            account.setAvailableLimit(account.getAvailableLimit().subtract(transaction.getAmount().abs()));
            this.accountRepository.save(account);
            return Optional.ofNullable(this.transactionRepository.save(transaction));
        } else {
            LOGGER.error("Insufficient funds : {}", transaction.toString());
            throw new CoreException(
                    new ApiErrorResponse(EValidationResponse.TRANSACTION_NOT_EFETIVATED_INSUFFICIENT_FUNDS));
        }
    }

    private Optional<TransactionEntity> creditOperation(TransactionEntity transaction) {
        LOGGER.debug("Updating account balance");
        AccountEntity account = transaction.getAccount();
        account.setBalance(account.getBalance().add(transaction.getAmount())
                .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        if(account.getAvailableLimit().add(transaction.getAmount()).compareTo(ELimitProfile.PADRAO.getLimit()) >= 0){
            LOGGER.error("LIMIT INVALID FOR : {}", account.toString());
            throw new CoreException(new ApiErrorResponse(EValidationResponse.INVALID_LIMIT));
        }
        account.setAvailableLimit(account.getAvailableLimit().add(transaction.getAmount()));
        this.accountRepository.save(account);
        LOGGER.debug("Inserting transaction");
        return Optional.ofNullable(this.transactionRepository.save(transaction));
    }

}
