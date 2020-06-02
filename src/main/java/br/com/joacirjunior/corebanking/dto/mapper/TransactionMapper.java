package br.com.joacirjunior.corebanking.dto.mapper;

import br.com.joacirjunior.corebanking.dto.TransactionDTO;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import br.com.joacirjunior.corebanking.entity.TransactionEntity;
import br.com.joacirjunior.corebanking.enums.EOperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionMapper {

    private static final Logger logger = LoggerFactory.getLogger(TransactionMapper.class);

    public static TransactionEntity convertToEntity(TransactionDTO transactionDTO, AccountEntity account){
        logger.debug("Transaction mapper : " + transactionDTO.toString());
        return new TransactionEntity(account,
                EOperationType.getOperationTypeByOperationId(transactionDTO.getOperationTypeId()),
                transactionDTO.getAmount());
    }


}
