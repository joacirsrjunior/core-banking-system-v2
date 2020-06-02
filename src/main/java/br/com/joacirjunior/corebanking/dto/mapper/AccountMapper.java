package br.com.joacirjunior.corebanking.dto.mapper;

import br.com.joacirjunior.corebanking.dto.AccountDTO;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class AccountMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountMapper.class);

    public static AccountEntity convertToEntity(AccountDTO accountDTO){
        if(accountDTO == null || !StringUtils.isEmpty(accountDTO.getId())){
            return new AccountEntity(Long.parseLong(accountDTO.getId()), accountDTO.getDocumentNumber(), BigDecimal.ZERO);
        }
        return new AccountEntity(accountDTO.getDocumentNumber(), BigDecimal.ZERO);
    }


}
