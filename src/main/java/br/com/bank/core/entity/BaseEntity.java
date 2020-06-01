package br.com.bank.core.entity;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class BaseEntity {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BaseEntity(){
    }

    public BaseEntity(String id) {
        this.id = id;
    }

}
