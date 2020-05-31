package br.com.bank.core.validations;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.ETransactionType;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class TransactionValidation {

    private static final Logger logger = LoggerFactory.getLogger(TransactionValidation.class);

    public TransactionValidation(){}

    public Mono<Transaction> validate(Transaction transaction) {
        logger.debug("Init validate");
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        if(!this.validateType(transaction)){
            logger.error("Error on validate type");
            apiErrorResponse.setError(EValidationResponse.TRANSACTION_TYPE_INVALID);
            return Mono.error(new CoreException(apiErrorResponse));
        } else if (!this.validateAccount(transaction)) {
            logger.error("Error on validate account");
            apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ACCOUNT_PARAM);
            return Mono.error(new CoreException(apiErrorResponse));
        } else if (!this.validateAmountNegativeOrZero(transaction)) {
            logger.error("Error on validate amount");
            apiErrorResponse.setError(EValidationResponse.VALIDATION_TRANSACTION_ERROR_AMOUNT_NEGATIVE_OR_ZERO);
            return Mono.error(new CoreException(apiErrorResponse));
        }
        logger.debug("Validate finished with success");
        return Mono.just(transaction);
    }

    private boolean validateType(Transaction transaction){
        logger.debug("Validating type...");
        return (transaction.getTransactionType().equals(ETransactionType.CREDIT)
                || transaction.getTransactionType().equals(ETransactionType.DEBIT));
    }

    private boolean validateAccount(Transaction transaction) {
        logger.debug("Validating account...");
        return !(transaction.getAccount().getBranchNumber().isBlank()
                || transaction.getAccount().getAccountNumber().isBlank());
    }

    private boolean validateAmountNegativeOrZero(Transaction transaction) {
        logger.debug("Validating amount...");
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return true;
    }

}
