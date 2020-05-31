package br.com.bank.core.api;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse<T> {

    private Meta meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
