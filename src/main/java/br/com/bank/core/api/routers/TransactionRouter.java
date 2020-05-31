package br.com.bank.core.api.routers;

import br.com.bank.core.api.handlers.TransactionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class TransactionRouter extends BaseRouter {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRouter.class);

    @Bean
    public RouterFunction<ServerResponse> transactionRoute(TransactionHandler transactionHandler){
        logger.debug("transactionRoute called");
        return RouterFunctions
                .route(POST("/transaction")
                        .and(accept(MediaType.APPLICATION_JSON)), transactionHandler::executeTransactionRequest);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

}
