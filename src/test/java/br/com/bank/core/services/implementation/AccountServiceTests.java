package br.com.bank.core.services.implementation;


import br.com.bank.core.entity.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountServiceTests {

    private static final String ACCOUNT = "12345-0";

    private AccountService service;

    private AccountEntity account;

    @Autowired
    public AccountServiceTests(AccountService service) {
        this.service = service;
    }

    @BeforeEach
    public void before(){
        account = new AccountEntity(this.ACCOUNT);
    }

    @Test
    public void insertAccount(){
        account.setBalance(new BigDecimal(1000));
        AccountEntity accountAfterSave = service.save(account).block();
        AccountEntity accountSaved = this.verifyFindAccountById(accountAfterSave.getId().toString());
        assertNotNull(accountSaved);
        assertTrue(!accountSaved.getDocumentNumber().isBlank());
    }

    private AccountEntity verifyFindAccountById(String accountID){
        AccountEntity searchedAccount = service.findById(accountID).block();
        assertNotNull(searchedAccount);
        assertFalse(searchedAccount.getDocumentNumber().isBlank());
        assertTrue(accountID.compareTo(searchedAccount.getId().toString()) == 0);
        return searchedAccount;
    }

}
