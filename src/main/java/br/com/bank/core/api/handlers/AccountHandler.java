package br.com.bank.core.api.handlers;

import br.com.bank.core.api.dto.AccountDTO;
import br.com.bank.core.entity.AccountEntity;
import br.com.bank.core.exceptions.CoreException;
import br.com.bank.core.services.implementation.AccountService;
import br.com.bank.core.utils.HandlerResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AccountHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccountHandler.class);

    private AccountService accountService;

    @Autowired
    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        logger.debug("Endpoint called - findAll");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.findAll(), AccountEntity.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        logger.debug("Endpoint called - findById");
        String id = request.pathVariable("accountId");
        return accountService.findById(id)
                .flatMap(resp -> {
                    return HandlerResponseUtils.ok(resp, request);
                })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(CoreException.class,
                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
    }

    public Mono<ServerResponse> create(ServerRequest request){
        logger.debug("Endpoint called - create");
        return request.bodyToMono(AccountDTO.class)
                .flatMap(ac -> {
                    return accountService
                            .create(ac)
                            .flatMap(obj -> {
                                return HandlerResponseUtils.ok(obj, request);
                            });
                });
    }

    public Mono<ServerResponse> getCurrentBalance(ServerRequest request){
        logger.debug("Endpoint called - getCurrentBalance");
        AccountEntity accountFilter =
                new AccountEntity(request.pathVariable("accountDocumentNumber"));
        return accountService.getCurrentBalance(accountFilter)
                .flatMap(resp -> {
                            return HandlerResponseUtils.ok(resp, request);
                        })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(CoreException.class,
                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
    }

}
