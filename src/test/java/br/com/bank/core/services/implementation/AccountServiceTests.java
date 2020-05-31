package br.com.bank.core.services.implementation;


import br.com.bank.core.entity.Account;
import br.com.bank.core.entity.Transaction;
import br.com.bank.core.enums.ETransactionType;
import br.com.bank.core.exceptions.CoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountServiceTests {

    private static final String BRANCH = "0001";
    private static final String ACCOUNT = "12345-0";

    private AccountService service;

    private Account account;

    @Autowired
    public AccountServiceTests(AccountService service) {
        this.service = service;
    }

    @BeforeEach
    public void before(){
        account = new Account(this.BRANCH, this.ACCOUNT);
    }

    @Test
    public void insertAccount(){
        account.setBalance(new BigDecimal(1000));
        Account accountAfterSave = service.save(account).block();
        Account accountSaved = this.verifyFindAccountById(accountAfterSave.getId());
        assertNotNull(accountSaved);
        assertTrue(!accountSaved.getBranchNumber().isBlank());
        assertTrue(!accountSaved.getAccountNumber().isBlank());
    }

    private Account verifyFindAccountById(String accountID){
        Account searchedAccount = service.findById(accountID).block();
        assertNotNull(searchedAccount);
        assertFalse(searchedAccount.getBranchNumber().isBlank());
        assertFalse(searchedAccount.getAccountNumber().isBlank());
        assertTrue(accountID.compareTo(searchedAccount.getId()) == 0);
        return searchedAccount;
    }

}
