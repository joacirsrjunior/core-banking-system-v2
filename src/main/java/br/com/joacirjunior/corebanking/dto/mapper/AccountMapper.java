package br.com.joacirjunior.corebanking.dto.mapper;

import br.com.joacirjunior.corebanking.dto.AccountDTO;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class AccountMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountMapper.class);

    public static AccountEntity convertToEntity(AccountDTO accountDTO){
        LOGGER.debug("Model mapper - convert to entity : {}", accountDTO);
        ModelMapper modelMapper = new ModelMapper();
        AccountEntity entity = modelMapper.map(accountDTO, AccountEntity.class);
        if(accountDTO.getAmount() != null) {
            entity.setBalance(accountDTO.getAmount());
        }
        return entity;
    }


}
