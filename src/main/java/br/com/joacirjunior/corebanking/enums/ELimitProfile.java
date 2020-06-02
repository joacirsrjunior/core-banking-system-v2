package br.com.joacirjunior.corebanking.enums;

import br.com.joacirjunior.corebanking.controller.api.ApiErrorResponse;
import br.com.joacirjunior.corebanking.exceptions.CoreException;

import java.math.BigDecimal;
import java.util.Arrays;

public enum ELimitProfile {

    PADRAO("Limite Padrão", new BigDecimal(5000));

    private String description;
    private BigDecimal limit;

    ELimitProfile(String description, BigDecimal limit) {
        this.description = description;
        this.limit = limit;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getLimit() {
        return limit;
    }

}
