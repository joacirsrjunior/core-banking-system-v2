package br.com.joacirjunior.corebanking.services;


import br.com.joacirjunior.corebanking.dto.AccountDTO;
import br.com.joacirjunior.corebanking.entity.AccountEntity;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    Optional<AccountEntity> create(AccountDTO accountDTO);

    Optional<List<AccountEntity>> findAll();

    Optional<AccountEntity> findById(String identifier);
}
