package br.com.bank.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Document("account")
public class AccountEntity extends BaseEntity {

    @NotNull
    private String documentNumber;

    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal balance;

    public AccountEntity(){
        super();
    }

    public AccountEntity(String documentNumber) {
        super();
        this.documentNumber = documentNumber;
    }

    public AccountEntity(String documentNumber, BigDecimal balance) {
        super();
        this.documentNumber = documentNumber;
        this.balance = balance;
    }

    public AccountEntity(String id, String documentNumber, BigDecimal balance) {
        super(id);
        this.documentNumber = documentNumber;
        this.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity account = (AccountEntity) o;
        return Objects.equals(documentNumber, account.documentNumber) &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNumber, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + this.getId() + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", balance=" + balance +
                '}';
    }

}