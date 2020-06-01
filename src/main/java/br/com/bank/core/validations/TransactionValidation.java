package br.com.bank.core.validations;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.TransactionEntity;
import br.com.bank.core.enums.EOperationType;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class TransactionValidation {

    private static final Logger logger = LoggerFactory.getLogger(TransactionValidation.class);

    public final static Mono<TransactionEntity> validateAll(TransactionEntity transaction) {
        logger.debug("Init validate");
        if(validateOperationType(transaction)){
            logger.error("Error on validate operation type");
            return Mono.error(new CoreException(
                    new ApiErrorResponse(EValidationResponse.TRANSACTION_TYPE_INVALID)));
        } else if (validateAccount(transaction)) {
            logger.error("Error on validate account");
            return Mono.error(new CoreException(
                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_ACCOUNT_PARAM)));
        } else if (validateZeroAmount(transaction)
                || validateNegativeAmount(transaction)
                || validatePositiveAmount(transaction)) {
            logger.error("Error on validate amount");
            return Mono.error(new CoreException(
                    new ApiErrorResponse(EValidationResponse.VALIDATION_TRANSACTION_AMOUNT_ERROR)));
        }
        logger.debug("Validate finished with success");
        return Mono.just(transaction);
    }

    /**
     * Validate if operation type is valid
     * @oaram The transaction entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private static boolean validateOperationType(TransactionEntity transaction){
        logger.debug("Validating operation type...");
        return !(transaction.getOperationType().equals(EOperationType.CASH_BUY)
                || transaction.getOperationType().equals(EOperationType.INSTALLMENT_BUY)
                || transaction.getOperationType().equals(EOperationType.WITHDRAW)
                || transaction.getOperationType().equals(EOperationType.PAYMENT));
    }

    /**
     * Validate account document number.
     * @oaram The transaction entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private static boolean validateAccount(TransactionEntity transaction) {
        logger.debug("Validating account...");
        return StringUtils.isEmpty(transaction.getAccount().getDocumentNumber());
    }

    /**
     * Validate if amount is equals zero value
     * @oaram The transaction entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private static boolean validateZeroAmount(TransactionEntity transaction) {
        logger.debug("Validating amount...");
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.debug("Fail - zero amount");
            return true;
        }
        return false;
    }

    /**
     * Validate negative values
     * @oaram The transaction entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private static boolean validateNegativeAmount(TransactionEntity transaction){
        if (transaction.getAmount().signum() == -1
                && transaction.getOperationType().getTransactionType().getSignal() > 0) {
            logger.debug("Fail - negative amount");
            return false;
        }
        return true;
    }

    /**
     * Validate positive values
     * @oaram The transaction entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private static boolean validatePositiveAmount(TransactionEntity transaction){
        if (transaction.getAmount().signum() == +1
                && transaction.getOperationType().getTransactionType().getSignal() < 0) {
            logger.debug("Fail - positive amount");
            return false;
        }
        return true;
    }

}
