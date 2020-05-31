package br.com.bank.core.data;

import br.com.bank.core.entity.Account;
import br.com.bank.core.repository.AccountRepository;
import br.com.bank.core.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class AccountDummyData implements CommandLineRunner{

    private final static String BRANCH_NUMBER = "0001";

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Autowired
    AccountDummyData ( AccountRepository accountRepository,
                       TransactionRepository transactionRepository ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        transactionRepository.deleteAll()
                .subscribe(elem -> System.out.println("Transactions deleted."));

        accountRepository.deleteAll()
                .thenMany(
                        Flux.just( "15425-5", "45869-2", "44478-4", "33569-5", "19195-2", "10214-5", "99865-1",
                                   "40041-5", "44471-5", "99945-1", "15151-0", "44758-1", "14142-7", "77458-1",
                                   "70023-9")
                                .map(accountNumber -> new Account(
                                        BRANCH_NUMBER,
                                        accountNumber,
                                        new BigDecimal(new Random().nextInt(1000))))
                                .flatMap(accountRepository::save))
                .subscribe(elem -> System.out.println(elem.toString()));

    }

}