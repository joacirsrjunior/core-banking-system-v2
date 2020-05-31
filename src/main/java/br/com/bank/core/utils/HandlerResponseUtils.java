package br.com.bank.core.utils;

import br.com.bank.core.api.ApiErrorResponse;
import br.com.bank.core.api.ApiResponse;
import br.com.bank.core.api.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class HandlerResponseUtils {
    
    private static ServerResponse.BodyBuilder createBaseResponse(HttpStatus status) {
        return ServerResponse.status(status)
                .contentType(APPLICATION_JSON);
    }

    public static <T> Mono<ServerResponse> createResponse(HttpStatus status, T body, ServerRequest request) {
        ApiResponse<?> apiResponse = new ApiResponse<>(body);
        apiResponse.setMeta(new Meta(request.path(),"success"));

        return HandlerResponseUtils.createBaseResponse(status)
                .body(fromObject(apiResponse));
    }

    public static Mono<ServerResponse> createErrorResponse(HttpStatus status, ApiErrorResponse error, ServerRequest request) {
        error.setMeta(new Meta(request.path(), "error"));
        return HandlerResponseUtils.createBaseResponse(status)
                .body(fromObject(error));
    }

    public static <T> Mono<ServerResponse> ok(T body, ServerRequest request) {
        return HandlerResponseUtils.createResponse(HttpStatus.OK, body, request);
    }

    public static <T> Mono<ServerResponse> created(T body, ServerRequest request) {
        return HandlerResponseUtils.createResponse(HttpStatus.CREATED, body, request);
    }

    public static <T> Mono<ServerResponse> accepted(T body, ServerRequest request) {
        return HandlerResponseUtils.createResponse(HttpStatus.ACCEPTED, body, request);
    }

    public static Mono<ServerResponse> notFound(ApiErrorResponse error, ServerRequest request) {
        return HandlerResponseUtils.createErrorResponse(HttpStatus.NOT_FOUND, error, request);
    }

    public static Mono<ServerResponse> badRequest(ApiErrorResponse error, ServerRequest request) {
        return HandlerResponseUtils.createErrorResponse(HttpStatus.BAD_REQUEST, error, request);
    }

    public static Mono<ServerResponse> internalServerError(ApiErrorResponse error, ServerRequest request) {
        return HandlerResponseUtils.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error, request);
    }
}
