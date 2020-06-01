package br.com.bank.core.entity;

import br.com.bank.core.enums.EOperationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Document("transaction")
public class TransactionEntity extends BaseEntity {

    @NotNull
    private AccountEntity account;

    @NotNull
    private EOperationType operationType;

    @NotNull
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal amount;

    public TransactionEntity(){
        super();
    }

    public TransactionEntity(AccountEntity account,
                             EOperationType operationType,
                             BigDecimal amount) {
        super();
        this.account = account;
        this.operationType = operationType;
        this.amount = amount;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public EOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(EOperationType operationType) {
        this.operationType = operationType;
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
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equals(account, that.account) &&
                operationType == that.operationType &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, operationType, amount);
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "account=" + account +
                ", operationType=" + operationType +
                ", amount=" + amount +
                '}';
    }

}
