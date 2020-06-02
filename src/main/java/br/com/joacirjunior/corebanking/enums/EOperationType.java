package br.com.joacirjunior.corebanking.enums;

import br.com.joacirjunior.corebanking.controller.api.ApiErrorResponse;
import br.com.joacirjunior.corebanking.exceptions.CoreException;

import java.util.Arrays;

public enum EOperationType {

    CASH_BUY(1, "Compra à vista", ETransactionType.DEBIT),
    INSTALLMENT_BUY(2, "Compra Parcelada", ETransactionType.DEBIT),
    WITHDRAW(3, "Saque", ETransactionType.DEBIT),
    PAYMENT(4, "Pagamento", ETransactionType.CREDIT);

    private Integer id;
    private String operationType;
    private ETransactionType transactionType;

    EOperationType(Integer id, String operationType, ETransactionType transactionType) {
        this.id = id;
        this.operationType = operationType;
        this.transactionType = transactionType;
    }

    public Integer getId() {
        return id;
    }

    public String getOperationType() {
        return operationType;
    }

    public ETransactionType getTransactionType() {
        return transactionType;
    }

    public static EOperationType getOperationTypeByOperationId(Integer operationId) {
        for (EOperationType op : Arrays.asList(EOperationType.values())) {
            if (operationId.equals(op.getId())) {
                return op;
            }
        }
        throw new CoreException(new ApiErrorResponse(EValidationResponse.INVALID_OPERATION_TYPE));
    }
}
