package br.com.bank.core.services.implementation;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.api.dto.AccountDTO;
import br.com.bank.core.api.dto.mapper.AccountMapper;
import br.com.bank.core.entity.AccountEntity;
import br.com.bank.core.enums.EValidationResponse;
import br.com.bank.core.exceptions.CoreException;
import br.com.bank.core.repository.AccountRepository;
import br.com.bank.core.services.IAccountService;
import br.com.bank.core.validations.AccountValidation;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;


    @Autowired
    public AccountService( AccountRepository accountRepository ) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Flux<AccountEntity> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<AccountEntity> findById(String id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new CoreException("Account not found")))
                .onErrorResume(error -> {
                    logger.error("[ERROR] Searching for account id {} : {}", id, error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_ACCOUNT_NOT_FOUND)));
                });
    }

    @Override
    public Mono<AccountEntity> create(AccountDTO accountDTO) {
        AccountEntity accountEntity = AccountMapper.convertToEntity(accountDTO);
        logger.debug("Verify if the account already exists with this document number");
        return accountRepository
                .findByAccountDocumentNumber(accountEntity)
                .flatMap(account -> {
                    if(account != null && !StringUtils.isEmpty(account.getDocumentNumber())
                            && account.getDocumentNumber().compareToIgnoreCase(accountDTO.getDocumentNumber()) == 0){
                        return Mono.error(
                                new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ACCOUNT_ALREADY_EXISTS)));
                    } else {
                        return accountRepository.save(accountEntity);
                    }
                })
                .switchIfEmpty(Mono.error(
                        new CoreException(
                                new ApiErrorResponse(EValidationResponse.VALIDATION_ACCOUNT_ALREADY_EXISTS))));
    }

    @Override
    public Mono<AccountEntity> save(AccountEntity account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<AccountEntity> delete(AccountEntity account) {
        return accountRepository.delete(account);
    }

    public Mono<AccountEntity> getCurrentBalance(AccountEntity accountFilter){
        logger.debug("Current balance executing : " + accountFilter.toString());
        return accountRepository.findByAccountDocumentNumber(accountFilter)
                .switchIfEmpty(Mono.error(
                        new CoreException(
                                new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_BALANCE_ACCOUNT))))
                .onErrorResume(error -> {
                    logger.error("[ERROR] Getting current balance : {}",  error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_GENERIC)));
                });
    }

    public Mono<AccountEntity> verifyAccountExistence(AccountEntity account){
        logger.debug("Verifing account existence. Account document number : {}", account.getDocumentNumber());
        return AccountValidation.validateAll(account)
                .flatMap(accountRepository::findByAccountDocumentNumber)
                .onErrorResume(error -> {
                    logger.error("[ERROR] Verifing account existence : {}", error.getMessage());
                    return Mono.error(
                            new CoreException(
                                    new ApiErrorResponse(EValidationResponse.VALIDATION_ERROR_ACCOUNT_NOT_FOUND)));
                });
    }

}
