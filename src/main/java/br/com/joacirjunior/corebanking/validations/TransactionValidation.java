package br.com.joacirjunior.corebanking.validations;

import br.com.joacirjunior.corebanking.controller.api.ApiErrorResponse;
import br.com.joacirjunior.corebanking.entity.TransactionEntity;
import br.com.joacirjunior.corebanking.enums.EOperationType;
import br.com.joacirjunior.corebanking.enums.EValidationResponse;
import br.com.joacirjunior.corebanking.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TransactionValidation {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionValidation.class);

    public final static Optional<TransactionEntity> validateAll(TransactionEntity transaction) {
        LOGGER.debug("Validation initializing");
        if(validateOperationType(transaction)){
            LOGGER.error("Error on validate operation type");
            throw new CoreException(new ApiErrorResponse(EValidationResponse.TRANSACTION_TYPE_INVALID));
        } else if (validateAccount(transaction)) {
            LOGGER.error("Error on validate account");
            throw new CoreException(new ApiErrorResponse(EValidationResponse.VALIDATION_INVALID_ACCOUNT));
        } else if (validateZeroAmount(transaction)
                || validateNegativeAmount(transaction)
                || validatePositiveAmount(transaction)) {
            LOGGER.error("Error on validate amount");
            throw new CoreException(new ApiErrorResponse(EValidationResponse.INVALID_TRANSACTION_VALUE));
        }
        LOGGER.debug("Validate finished with success");
        return Optional.ofNullable(transaction);
    }

    /**
     * Validate if operation type is valid
     * @oaram The transaction entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private static boolean validateOperationType(TransactionEntity transaction){
        LOGGER.debug("Validating operation type");
        return !(transaction.getOperationType().equals(EOperationType.CASH_BUY)
                || transaction.getOperationType().equals(EOperationType.INSTALLMENT_BUY)
                || transaction.getOperationType().equals(EOperationType.WITHDRAW)
                || transaction.getOperationType().equals(EOperationType.PAYMENT));
    }

    /**
     * Validate transaction
     * @oaram The transaction entity
     * @return true Validate fail
     * @return false Success on validate
     * */
    private static boolean validateAccount(TransactionEntity transaction) {
        LOGGER.debug("Validating transaction - {}", transaction.toString());
        return StringUtils.isEmpty(transaction.getAccount().getDocumentNumber());
    }

    /**
     * Validate if amount is equals zero value
     * @oaram The transaction entity
     * @return true Validate fail
     * @return false Success on validate
     * */
    private static boolean validateZeroAmount(TransactionEntity transaction) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            LOGGER.error("ERROR - Zero amount - {}", transaction.toString());
            return true;
        }
        return false;
    }

    /**
     * Validate negative values
     * @oaram The transaction entity
     * @return true Validate fail
     * @return false Success on validate
     * */
    private static boolean validateNegativeAmount(TransactionEntity transaction){
        if (transaction.getAmount().signum() == -1
                && transaction.getOperationType().getTransactionType().getSignal() > 0) {
            LOGGER.error("ERROR - negative amount - {}", transaction.toString());
            return true;
        }
        return false;
    }

    /**
     * Validate positive values
     * @oaram The transaction entity
     * @return true Validate fail
     * @return false Success on validate
     * */
    private static boolean validatePositiveAmount(TransactionEntity transaction){
        if (transaction.getAmount().signum() == +1
                && transaction.getOperationType().getTransactionType().getSignal() < 0) {
            LOGGER.error("ERROR - negative amount expected - {}", transaction.toString());
            return true;
        }
        return false;
    }

}
