package br.com.bank.core.validations;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class AccountValidation {

    public AccountValidation(){}

    public Mono<Account> validate(Account account) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        if (!this.validateAccount(account)) {
            apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ACCOUNT_PARAM);
            return Mono.error(new CoreException(apiErrorResponse));
        }
        return Mono.just(account);
    }

    public Mono<Account> validateAll(Account account) {
        return validate(account)
                .flatMap(obj -> {
                    if (!this.validateId(obj)) {
                        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
                        apiErrorResponse.setError(EValidationResponse.VALIDATION_ERROR_ID_PARAM);
                        return Mono.error(new CoreException(apiErrorResponse));
                    }
                    return Mono.just(obj);
                });
    }

    private boolean validateAccount(Account account) {
        return !(account.getBranchNumber().isBlank() || account.getAccountNumber().isBlank());
    }

    private boolean validateId(Account account){
        return !(account.getId().isBlank());
    }

}
