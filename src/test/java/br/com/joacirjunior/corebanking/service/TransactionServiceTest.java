package br.com.joacirjunior.corebanking.service;

import br.com.joacirjunior.corebanking.PostgresqlContainer;
import br.com.joacirjunior.corebanking.entity.AccountEntity;
import br.com.joacirjunior.corebanking.entity.TransactionEntity;
import br.com.joacirjunior.corebanking.enums.EOperationType;
import br.com.joacirjunior.corebanking.services.implementation.TransactionService;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresqlContainer.Initializer.class})
public class TransactionServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(TransactionServiceTest.class);

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getInstance();

    @Autowired
    private TransactionService transactionService;

    @Test
    @Transactional
    public void sensibilizeAccountBalanceDebitTest1(){
        TransactionEntity transaction = new TransactionEntity(Long.parseLong("1"),
                new AccountEntity("123456789012", new BigDecimal(1000)),
                EOperationType.CASH_BUY, new BigDecimal(-100));
        Optional<TransactionEntity> optTransaction = this.transactionService.sensibilizeAccountBalance(transaction);
        Assert.assertNotNull(optTransaction);
        Assert.assertFalse(optTransaction.isEmpty());
        BigDecimal expected = new BigDecimal(900).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        Assert.assertEquals(expected, optTransaction.get().getAccount().getBalance());
    }

    @Test
    @Transactional
    public void sensibilizeAccountBalanceDebitTest2(){
        TransactionEntity transaction = new TransactionEntity(Long.parseLong("1"),
                new AccountEntity("123456789012", new BigDecimal(743.98)),
                EOperationType.INSTALLMENT_BUY, new BigDecimal(-100.73));
        Optional<TransactionEntity> optTransaction = this.transactionService.sensibilizeAccountBalance(transaction);
        Assert.assertNotNull(optTransaction);
        Assert.assertFalse(optTransaction.isEmpty());
        BigDecimal expected = new BigDecimal(643.25).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        Assert.assertEquals(expected, optTransaction.get().getAccount().getBalance());
    }

    @Test
    @Transactional
    public void sensibilizeAccountBalanceDebitTest3(){
        TransactionEntity transaction = new TransactionEntity(Long.parseLong("1"),
                new AccountEntity("123456789012", new BigDecimal(10)),
                EOperationType.WITHDRAW, new BigDecimal(-1));
        Optional<TransactionEntity> optTransaction = this.transactionService.sensibilizeAccountBalance(transaction);
        Assert.assertNotNull(optTransaction);
        Assert.assertFalse(optTransaction.isEmpty());
        BigDecimal expected = new BigDecimal(9).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        Assert.assertEquals(expected, optTransaction.get().getAccount().getBalance());
    }

    @Test
    @Transactional
    public void sensibilizeAccountBalanceCreditTest1(){
        TransactionEntity transaction = new TransactionEntity(Long.parseLong("1"),
                new AccountEntity("123456789012", new BigDecimal(10)),
                EOperationType.PAYMENT, new BigDecimal(125.75));
        Optional<TransactionEntity> optTransaction = this.transactionService.sensibilizeAccountBalance(transaction);
        Assert.assertNotNull(optTransaction);
        Assert.assertFalse(optTransaction.isEmpty());
        BigDecimal expected = new BigDecimal(135.75).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        Assert.assertEquals(expected, optTransaction.get().getAccount().getBalance());
    }

    @Test
    @Transactional
    public void sensibilizeAccountBalanceCreditTest2(){
        TransactionEntity transaction = new TransactionEntity(Long.parseLong("1"),
                new AccountEntity("123456789012", new BigDecimal(0)),
                EOperationType.PAYMENT, new BigDecimal(10000));
        Optional<TransactionEntity> optTransaction = this.transactionService.sensibilizeAccountBalance(transaction);
        Assert.assertNotNull(optTransaction);
        Assert.assertFalse(optTransaction.isEmpty());
        BigDecimal expected = new BigDecimal(10000).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        Assert.assertEquals(expected, optTransaction.get().getAccount().getBalance());
    }

    @Test
    @Transactional
    public void sensibilizeAccountBalanceCreditTest3(){
        TransactionEntity transaction = new TransactionEntity(Long.parseLong("1"),
                new AccountEntity("123456789012", new BigDecimal(25.75)),
                EOperationType.PAYMENT, new BigDecimal(25.75));
        Optional<TransactionEntity> optTransaction = this.transactionService.sensibilizeAccountBalance(transaction);
        Assert.assertNotNull(optTransaction);
        Assert.assertFalse(optTransaction.isEmpty());
        BigDecimal expected = new BigDecimal(51.50).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        Assert.assertEquals(expected, optTransaction.get().getAccount().getBalance());
    }

}
