package br.com.bank.core.api.dto.mapper;

import br.com.bank.core.api.dto.AccountDTO;
import br.com.bank.core.entity.AccountEntity;
import br.com.bank.core.services.implementation.AccountService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountMapper {

    private static final Logger logger = LoggerFactory.getLogger(AccountMapper.class);

    public static AccountEntity convertToEntity(AccountDTO accountDTO){
        logger.debug("Account mapper : " + accountDTO.toString());
        if(accountDTO == null || accountDTO.getId() == null
                || StringUtils.isEmpty(accountDTO.getId())){
            accountDTO.setId(UUID.randomUUID().toString());
        }
        logger.debug("Account mapper : " + accountDTO.toString());
        return new AccountEntity(accountDTO.getId(), accountDTO.getDocumentNumber(), BigDecimal.ZERO);
    }


}
