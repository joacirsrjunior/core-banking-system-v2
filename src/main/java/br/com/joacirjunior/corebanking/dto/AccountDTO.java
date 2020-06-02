package br.com.joacirjunior.corebanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class AccountDTO {

    @Id
    @JsonProperty("account_id")
    private String id;

    @NotNull(message = "Invalid document number")
    @JsonProperty("document_number")
    private String documentNumber;

    private BigDecimal amount;

    public AccountDTO(){
        super();
    }

    public AccountDTO(String documentNumber) {
        super();
        this.documentNumber = documentNumber;
    }

    public AccountDTO(String documentNumber, BigDecimal amount) {
        this.documentNumber = documentNumber;
        this.amount = amount;
    }

    public AccountDTO(String id, String documentNumber, BigDecimal amount) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
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
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(documentNumber, that.documentNumber) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentNumber, amount);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id='" + id + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", amount=" + amount +
                '}';
    }

}
