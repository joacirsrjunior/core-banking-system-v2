package br.com.joacirjunior.corebanking.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum EValidationResponse {

    VALIDATION_ERROR_GENERIC("ERR-0001", "WS-CORE-BANKING-SYSTEM", "TEC", "GENERIC ERROR"),
    VALIDATION_ERROR_REQUEST_PARAMS("ERR-0002", "WS-CORE-BANKING-SYSTEM", "TEC", "VALIDATION", "Request with invalid parameters"),

    UNAUTHORIZED("ERR-1000", null, null, "Not authorized", "Not authorized"),

    VALIDATION_INVALID_ACCOUNT("ERR-2000", "WS-CORE-BANKING-SYSTEM", "TEC", "VALIDATION", "Invalid account"),
    VALIDATION_ACCOUNT_NOT_FOUND("ERR-2001", "WS-CORE-BANKING-SYSTEM", "TEC", "VALIDATION", "Account not found"),
    VALIDATION_BALANCE_ACCOUNT_NOT_FOUND("ERR-2002", "WS-CORE-BANKING-SYSTEM", "TEC", "VALIDATION", "Account not found for check the current balance"),
    VALIDATION_ACCOUNT_ALREADY_EXISTS("ERR-2003", "WS-CORE-BANKING-SYSTEM", "TEC", "VALIDATION", "Account already exists"),

    INVALID_OPERATION_TYPE("ERR-3000", "WS-CORE-BANKING-SYSTEM", "TEC", "OPERATION TYPE", "Invalid operation type"),

    TRANSACTION_CREATE_ERROR("ERR-4000", "WS-CORE-BANKING-SYSTEM", "TEC", "VALIDATION", "Error creating transaction"),
    TRANSACTION_NOT_EFETIVATED_INSUFFICIENT_FUNDS("ERR-4001", "WS-CORE-BANKING-SYSTEM", "TEC", "TRANSACTION", "Transaction not effetivated - Insufficient funds"),
    TRANSACTION_TYPE_INVALID("ERR-4002", "WS-CORE-BANKING-SYSTEM", "TEC", "TRANSACTION", "Transaction type invalid"),
    INVALID_TRANSACTION_VALUE("ERR-4003", "WS-CORE-BANKING-SYSTEM", "TEC", "TRANSACTION", "Invalid transaction value"),
    TRANSACTION_NEGATIVE_AMOUNT_EXPECTED("ERR-4004", "WS-CORE-BANKING-SYSTEM", "TEC", "TRANSACTION", "Negative amount expected for this operation");

    @JsonProperty("codigo")
    private String code;

    @JsonProperty("origem")
    private String origin;

    @JsonProperty("tipo")
    private String type;

    @JsonProperty("subtipo")
    private String subType;

    private String msg;

    @JsonProperty("detalhes")
    private String detail;

    EValidationResponse() {
    }

    EValidationResponse(String code, String origin, String type, String subType) {
        this.code = code;
        this.origin = origin;
        this.type = type;
        this.subType = subType;
    }

    EValidationResponse(String code, String origin, String type, String subType, String msg) {
        this.code = code;
        this.origin = origin;
        this.type = type;
        this.subType = subType;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getOrigin() {
        return origin;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getMsg() {
        return msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
