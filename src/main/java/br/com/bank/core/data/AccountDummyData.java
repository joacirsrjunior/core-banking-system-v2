package br.com.bank.core.data;

import br.com.bank.core.api.dto.AccountDTO;
import br.com.bank.core.api.dto.mapper.AccountMapper;
import br.com.bank.core.entity.AccountEntity;
import br.com.bank.core.repository.AccountRepository;
import br.com.bank.core.repository.TransactionRepository;
import br.com.bank.core.services.implementation.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class AccountDummyData implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(AccountDummyData.class);

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @Autowired
    AccountDummyData(AccountService accountService, AccountRepository accountRepository,
                     TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {

        transactionRepository.deleteAll()
                .subscribe(elem -> logger.debug("Transactions deleted."));

        accountRepository.deleteAll()
                .thenMany(
                        Flux.just( "95953093039", "86396468050", "81766033091", "51888262087",
                                   "82989203095", "61374523011", "97475237010", "57098467081",
                                   "73259720081", "42296190065", "06497052038", "02468809025",
                                   "62131385013", "25443590057", "67595344093")
                                .map(accountNumber -> new AccountEntity(
                                        accountNumber,
                                        new BigDecimal(new Random().nextInt(1000))))
                                .flatMap(elem -> {
                                    AccountDTO dto = new AccountDTO(elem.getDocumentNumber());
                                    return accountService.create(dto);
                                }))
                .subscribe(elem -> logger.debug(elem.toString()));

    }

}