package br.com.bank.core.validations;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.AccountEntity;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
public class AccountValidation {

    public AccountValidation(){}

    public final static Mono<AccountEntity> validateAll(AccountEntity account) {
        return validate(account)
                .flatMap(obj -> {
                    if (!validateId(obj)) {
                        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
                        apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ID_PARAM);
                        return Mono.error(new CoreException(apiErrorResponse));
                    }
                    return Mono.just(obj);
                });
    }

    private final static Mono<AccountEntity> validate(AccountEntity account) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        if (!validateAccount(account)) {
            apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ACCOUNT_PARAM);
            return Mono.error(new CoreException(apiErrorResponse));
        }
        return Mono.just(account);
    }

    /**
     * Validate if the account document number is not empty
     * @oaram The account entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private final static boolean validateAccount(AccountEntity account) {
        return StringUtils.isEmpty(account);
    }

    /**
     * Validate if the account document number is not empty
     * @oaram The account entity
     * @return true Fail on validate
     * @return false Success on validate
     * */
    private final static boolean validateId(AccountEntity account){
        return StringUtils.isEmpty(account.getId().toString());
    }

}