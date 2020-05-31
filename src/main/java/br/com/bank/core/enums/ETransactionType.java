package br.com.bank.core.enums;

public enum ETransactionType {

    CREDIT("C"),
    DEBIT("D");

    private String operationType;

    ETransactionType(String operationType){
        this.operationType = operationType;
    }

    public String getOperationType(){
        return this.operationType;
    }

}
