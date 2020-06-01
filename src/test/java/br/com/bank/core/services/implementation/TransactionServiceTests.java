package br.com.bank.core.services.implementation;

import br.com.bank.core.entity.AccountEntity;
import br.com.bank.core.entity.TransactionEntity;
import br.com.bank.core.enums.EOperationType;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionServiceTests {

    private static final String ACCOUNT = "44758-1";

    private TransactionService service;

    private AccountService serviceAccount;

    private TransactionEntity transaction;

    @Autowired
    public TransactionServiceTests(TransactionService service, AccountService serviceAccount) {
        this.service = service;
        this.serviceAccount = serviceAccount;
    }

    @BeforeEach
    public void before(){
        transaction = new TransactionEntity(new AccountEntity(this.ACCOUNT), EOperationType.PAYMENT,null);
    }

    @Test
    public void verifyAlwaysPositiveResultForCreditTransaction(){

        for(int nTests = 10; nTests <= 10; nTests++ ) {
            BigDecimal creditAmount = new BigDecimal(new Random().nextInt(9));
            TransactionEntity response = this.executeCreditTransaction(creditAmount);
            assertFalse(response.getAmount().compareTo(creditAmount) < 0);
        }

    }

    @Test
    public void verifyAlwaysPositiveAmountForCreditTransaction(){

        Assertions.assertThrows(CoreException.class, () -> {
            BigDecimal creditAmount = new BigDecimal(new Random().nextInt(9));
            if(creditAmount.compareTo(BigDecimal.ZERO) > 0)
                creditAmount = creditAmount.multiply(new BigDecimal(-1));
            this.executeCreditTransaction(creditAmount);
        });

    }

    @Test
    public void verifyDebitForAccountWithoutBalanceAvailable(){

        Assertions.assertThrows(CoreException.class, () -> {
            BigDecimal debitAmount = new BigDecimal(1000000);
            TransactionEntity response = this.executeDeditTransaction(debitAmount);
        });

    }

    @Test
    public void verifyIfBalanceIsCorrectAfterACreditTransaction(){

        AccountEntity account = new AccountEntity(this.ACCOUNT);
        BigDecimal amountToCredit = new BigDecimal(1000);
        BigDecimal balanceBefore = this.getCurrentBalance(account);
        TransactionEntity response = this.executeCreditTransaction(amountToCredit);
        BigDecimal finalAmount = balanceBefore.add(amountToCredit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

        amountToCredit = new BigDecimal(10.22);
        balanceBefore = this.getCurrentBalance(account);
        response = this.executeCreditTransaction(amountToCredit);
        finalAmount = balanceBefore.add(amountToCredit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

        amountToCredit = new BigDecimal(27.50);
        balanceBefore = this.getCurrentBalance(account);
        response = this.executeCreditTransaction(amountToCredit);
        finalAmount = balanceBefore.add(amountToCredit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

        amountToCredit = new BigDecimal(1);
        balanceBefore = this.getCurrentBalance(account);
        response = this.executeCreditTransaction(amountToCredit);
        finalAmount = balanceBefore.add(amountToCredit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

    }

    @Test
    public void verifyIfBalanceIsCorrectAfterADebitTransaction(){

        AccountEntity account = new AccountEntity(this.ACCOUNT);
        account.setBalance(new BigDecimal(2500));
        serviceAccount.save(account);

        BigDecimal amountToDebit = new BigDecimal(500);
        BigDecimal balanceBefore = this.getCurrentBalance(account);
        TransactionEntity response = this.executeDeditTransaction(amountToDebit);
        BigDecimal finalAmount = balanceBefore.subtract(amountToDebit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

        amountToDebit = new BigDecimal(10);
        balanceBefore = this.getCurrentBalance(account);
        response = this.executeDeditTransaction(amountToDebit);
        finalAmount = balanceBefore.subtract(amountToDebit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

        amountToDebit = new BigDecimal(25.90);
        balanceBefore = this.getCurrentBalance(account);
        response = this.executeDeditTransaction(amountToDebit);
        finalAmount = balanceBefore.subtract(amountToDebit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

        amountToDebit = new BigDecimal(13.01);
        balanceBefore = this.getCurrentBalance(account);
        response = this.executeDeditTransaction(amountToDebit);
        finalAmount = balanceBefore.subtract(amountToDebit);
        assertTrue(finalAmount.compareTo(response.getAccount().getBalance()) == 0);

    }

    private TransactionEntity executeCreditTransaction(BigDecimal creditAmount){
        transaction.setOperationType(EOperationType.PAYMENT);
        transaction.setAmount(creditAmount);
        return service.executeTransaction(this.transaction).block();
    }

    private TransactionEntity executeDeditTransaction(BigDecimal creditAmount){
        transaction.setOperationType(EOperationType.WITHDRAW);
        transaction.setAmount(creditAmount);
        return service.executeTransaction(this.transaction).block();
    }

    private BigDecimal getCurrentBalance(AccountEntity account){
        AccountEntity resp = serviceAccount.getCurrentBalance(account).block();
        return resp.getBalance();
    }

}
