package br.com.bank.core.api.handlers;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.entity.TransactionEntity;
import br.com.bank.core.exceptions.CoreException;
import br.com.bank.core.services.implementation.TransactionService;
import br.com.bank.core.utils.HandlerResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    private TransactionService transactionService;

    @Autowired
    public TransactionHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Mono<ServerResponse> executeTransactionRequest(final ServerRequest request){
        logger.debug("Endpoint called - executeTransactionRequest");
        return request.body(BodyExtractors.toMono(TransactionEntity.class))
                .flatMap(t -> {
                    return this.transactionService
                            .executeTransaction(t)
                            .onErrorResume(error -> {
                                CoreException coreException = (CoreException) error;
                                return Mono.error(coreException);
                            });
                })
                .flatMap(trans -> {
                    logger.debug("Return Body - Transaction : {}", trans);
                    return HandlerResponseUtils.ok(trans, request);
                })
                .onErrorResume(error -> {
                    ApiErrorResponse apiErrorResponse = ((CoreException) error).getErrorResponse();
                    logger.error("Returning error : {} - {}",
                            apiErrorResponse.getError().getCode(),
                            apiErrorResponse.getError().getMsg());
                    return HandlerResponseUtils.badRequest(apiErrorResponse, request);
                });
    }

}
