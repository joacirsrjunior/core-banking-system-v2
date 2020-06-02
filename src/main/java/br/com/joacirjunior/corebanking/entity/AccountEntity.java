package br.com.joacirjunior.corebanking.entity;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {

    @NotNull
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @NotNull
    @Column(name = "balance", nullable = false)
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal balance;

    @Column(name = "available_limit")
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal availableLimit;

    public AccountEntity(){
        super();
    }

    public AccountEntity(String documentNumber, BigDecimal balance) {
        super();
        this.documentNumber = documentNumber;
        this.balance = balance;
    }

    public AccountEntity(Long id, String documentNumber, BigDecimal balance) {
        super(id);
        this.documentNumber = documentNumber;
        this.balance = balance;
    }

    public AccountEntity(String documentNumber, BigDecimal balance, BigDecimal availableLimit) {
        this.documentNumber = documentNumber;
        this.balance = balance;
        this.availableLimit = availableLimit;
    }

    public AccountEntity(Long id, String documentNumber, BigDecimal balance, BigDecimal availableLimit) {
        super(id);
        this.documentNumber = documentNumber;
        this.balance = balance;
        this.availableLimit = availableLimit;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(BigDecimal availableLimit) {
        this.availableLimit = availableLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(documentNumber, that.documentNumber) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(availableLimit, that.availableLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), documentNumber, balance, availableLimit);
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "documentNumber='" + documentNumber + '\'' +
                ", balance=" + balance +
                ", availableLimit=" + availableLimit +
                '}';
    }

}