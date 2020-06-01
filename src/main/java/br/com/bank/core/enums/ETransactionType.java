package br.com.bank.core.enums;

public enum ETransactionType {

    CREDIT( "Crédito", +1),
    DEBIT("Débito", -1);

    private String transactionType;
    private Integer signal;

    ETransactionType(String transactionType, Integer signal) {
        this.transactionType = transactionType;
        this.signal = signal;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Integer getSignal() {
        return signal;
    }

}
