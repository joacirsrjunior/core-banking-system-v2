package br.com.joacirjunior.corebanking.validations;

import br.com.joacirjunior.corebanking.controller.api.ApiErrorResponse;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import br.com.joacirjunior.corebanking.enums.EValidationResponse;
import br.com.joacirjunior.corebanking.exceptions.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class AccountValidation {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountValidation.class);

    public AccountValidation(){}

    public final static Optional<AccountEntity> validateAll(AccountEntity account) {
        if (validateAccount(account)) {
            LOGGER.error("ERROR - account validate fail - Account invalid : {}", account.toString());
            throw new CoreException(
                    new ApiErrorResponse(EValidationResponse.VALIDATION_INVALID_ACCOUNT));
        }
        return Optional.ofNullable(account);
    }

    /**
     * Validate if the account document number is not empty
     * @oaram The account entity
     * @return true Validate fail
     * @return false Success on validate
     * */
    private final static boolean validateAccount(AccountEntity account) {
        return StringUtils.isEmpty(account);
    }

}