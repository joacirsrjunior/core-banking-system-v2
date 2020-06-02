package br.com.joacirjunior.corebanking.entity;

import br.com.joacirjunior.corebanking.enums.EOperationType;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @NotNull
    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EOperationType operationType;

    @NotNull
    @Column(name = "amount", nullable = false)
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal amount;

    public TransactionEntity(){
        super();
    }

    public TransactionEntity(AccountEntity account, EOperationType operationType, BigDecimal amount) {
        super();
        this.account = account;
        this.operationType = operationType;
        this.amount = amount;
    }

    public TransactionEntity(Long id, AccountEntity account, EOperationType operationType, BigDecimal amount) {
        super(id);
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
        if (!super.equals(o)) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equals(account, that.account) &&
                operationType == that.operationType &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), account, operationType, amount);
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
