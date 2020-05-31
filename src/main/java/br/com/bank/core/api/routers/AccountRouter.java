package br.com.bank.core.api.routers;

import br.com.bank.core.api.handlers.AccountHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AccountRouter extends BaseRouter {

    private static final Logger logger = LoggerFactory.getLogger(AccountRouter.class);

    @Bean
    public RouterFunction<ServerResponse> accountRoute(AccountHandler accountHandler){
        logger.debug("accountRoute called");
        return RouterFunctions
                .route(GET("/account")
                        .and(accept(MediaType.APPLICATION_JSON)), accountHandler::findAll)
                .andRoute(GET("/account/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)), accountHandler::findById)
                .andRoute(GET("/account/{accountNumber}/branch/{branchNumber}/balance")
                        .and(accept(MediaType.APPLICATION_JSON)), accountHandler::getCurrentBalance)
                .andRoute(POST("/account").and(accept(MediaType.APPLICATION_JSON)), accountHandler::save);
    }

    @Override
    Logger getLogger() {
        return logger;
    }

}
