package br.com.bank.core.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class AccountDTO {

    @Id
    @JsonProperty("account_id")
    private String id;

    @JsonProperty("document_number")
    @NotNull(message = "Invalid document number")
    private String documentNumber;

    public AccountDTO(){
        super();
    }

    public AccountDTO(String documentNumber) {
        super();
        this.documentNumber = documentNumber;
    }

    public AccountDTO( String id, String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(documentNumber, that.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentNumber);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", documentNumber='" + documentNumber + '\'' +
                '}';
    }

}