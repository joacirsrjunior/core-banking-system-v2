package br.com.joacirjunior.corebanking.service;

import br.com.joacirjunior.corebanking.PostgresqlContainer;
import br.com.joacirjunior.corebanking.dto.AccountDTO;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import br.com.joacirjunior.corebanking.services.implementation.AccountService;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresqlContainer.Initializer.class})
public class AccountServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountServiceTest.class);

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getInstance();

    @Autowired
    private AccountService accountService;

    @Test
    public void createAccountTest1(){
        AccountDTO accountDTO = new AccountDTO("1", "123456789012", new BigDecimal(1000));
        Optional<AccountEntity> optAccount = this.accountService.create(accountDTO);
        Assert.assertNotNull(optAccount);
        Assert.assertFalse(optAccount.isEmpty());
        Assert.assertEquals(new BigDecimal(1000), optAccount.get().getBalance());
    }

    @Test
    public void createAccountTest2(){
        AccountDTO accountDTO = new AccountDTO("2", "123456789013", new BigDecimal(0));
        Optional<AccountEntity> optAccount = this.accountService.create(accountDTO);
        Assert.assertNotNull(optAccount);
        Assert.assertFalse(optAccount.isEmpty());
        Assert.assertEquals(new BigDecimal(0), optAccount.get().getBalance());
    }

    @Test
    public void createAccountTest3(){
        AccountDTO accountDTO = new AccountDTO("3", "123456789014", new BigDecimal(324.54));
        Optional<AccountEntity> optAccount = this.accountService.create(accountDTO);
        Assert.assertNotNull(optAccount);
        Assert.assertFalse(optAccount.isEmpty());
        Assert.assertEquals(new BigDecimal(324.54), optAccount.get().getBalance());
    }

}
