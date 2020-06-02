package br.com.joacirjunior.corebanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class TransactionDTO {

    @JsonProperty("account_id")
    @NotNull(message = "Invalid account ID")
    private String accountId;

    @JsonProperty("operation_type_id")
    @NotNull(message = "Invalid operation type")
    private Integer operationTypeId;

    @NotNull(message = "Invalid amount")
    private BigDecimal amount;

    public TransactionDTO(){
        super();
    }

    public TransactionDTO(String accountId, Integer operationTypeId, BigDecimal amount) {
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(operationTypeId, that.operationTypeId) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, operationTypeId, amount);
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "accountId='" + accountId + '\'' +
                ", operationTypeId=" + operationTypeId +
                ", amount=" + amount +
                '}';
    }

}
