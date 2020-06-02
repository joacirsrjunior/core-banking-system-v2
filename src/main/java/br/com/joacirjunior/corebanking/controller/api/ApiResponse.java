package br.com.joacirjunior.corebanking.controller.api;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse<T> {

    private Meta meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(T data) {
        this.meta = new Meta();
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
        meta = meta;
    }

}
