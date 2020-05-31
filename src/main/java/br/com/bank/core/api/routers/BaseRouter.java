package br.com.bank.core.api.routers;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.utils.HandlerResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public abstract class BaseRouter {

    abstract Logger getLogger();

    @Value("${SERVER_CONTEXT_PATH:}")
    private String contextPath;

    public String getUrl(String path) {
        return String.format("%s%s", contextPath, path);
    }

    HandlerFunction<ServerResponse> createHandlerFunction(HandlerFunction<ServerResponse> handlerFunction) {
        var logger = LoggerFactory.getLogger(this.getClass());

        return (request) -> {
            request.attributes().put("protocolId", RouterUtils.getProtocolIdFromRequest(request));

            return handlerFunction.handle(request)
                    .doOnError(error -> logger.error("Exception found", error))
                    .onErrorResume(error -> HandlerResponseUtils.internalServerError(
                            new ApiErrorResponse(error.getMessage()), request));
        };
    }

}
