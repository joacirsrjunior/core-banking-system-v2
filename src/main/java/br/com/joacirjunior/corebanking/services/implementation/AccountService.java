package br.com.joacirjunior.corebanking.services.implementation;

import br.com.joacirjunior.corebanking.controller.api.ApiErrorResponse;
import br.com.joacirjunior.corebanking.dto.AccountDTO;
import br.com.joacirjunior.corebanking.dto.mapper.AccountMapper;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import br.com.joacirjunior.corebanking.enums.EValidationResponse;
import br.com.joacirjunior.corebanking.exceptions.CoreException;
import br.com.joacirjunior.corebanking.repository.AccountRepository;
import br.com.joacirjunior.corebanking.services.IAccountService;
import br.com.joacirjunior.corebanking.validations.AccountValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;

    @Autowired
    public AccountService( AccountRepository accountRepository ) {
        this.accountRepository = accountRepository;
    }

    public Optional<AccountEntity> create(AccountDTO accountDTO){
        AccountEntity account = AccountMapper.convertToEntity(accountDTO);
        AccountValidation.validateAll(account);
        Optional<AccountEntity> opt = this.accountRepository.findByDocumentNumber(account.getDocumentNumber());
        if(opt.isEmpty()){
            return Optional.ofNullable(this.accountRepository.save(account));
        } else {
            LOGGER.error("Account already exists for this document number");
            throw new CoreException(new ApiErrorResponse(EValidationResponse.VALIDATION_ACCOUNT_ALREADY_EXISTS));
        }
    }

    @Override
    public Optional<List<AccountEntity>> findAll() {
        return Optional.ofNullable(this.accountRepository.findAll());
    }

    @Override
    public Optional<AccountEntity> findById(String identifier) {
        Optional<AccountEntity> opt = this.accountRepository.findById(Long.parseLong(identifier));
        if (opt.isEmpty()) {
            LOGGER.error("Account not found");
            throw new CoreException(new ApiErrorResponse(EValidationResponse.VALIDATION_ACCOUNT_NOT_FOUND));
        }
        return opt;
    }

}
