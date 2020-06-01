package br.com.bank.core.enums;

public enum EOperationType {

    CASH_BUY("Compra à vista", ETransactionType.DEBIT),
    INSTALLMENT_BUY("Compra Parcelada", ETransactionType.DEBIT),
    WITHDRAW("Saque", ETransactionType.DEBIT),
    PAYMENT("Pagamento", ETransactionType.CREDIT);

    private String operationType;
    private ETransactionType transactionType;

    EOperationType(String operationType, ETransactionType transactionType) {
        this.operationType = operationType;
        this.transactionType = transactionType;
    }

    public String getOperationType() {
        return operationType;
    }

    public ETransactionType getTransactionType() {
        return transactionType;
    }

}
