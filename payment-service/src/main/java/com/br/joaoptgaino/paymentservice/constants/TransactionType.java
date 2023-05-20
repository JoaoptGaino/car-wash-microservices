package com.br.joaoptgaino.paymentservice.constants;

public enum TransactionType {
    CASH("Cash"),
    PIX("Pix"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    WIRE_TRANSFER("Wire Transfer");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getName() {
        return type;
    }

    public static TransactionType fromString(String value) {
        if (value != null) {
            for (TransactionType transactionType : TransactionType.values()) {
                if (value.equalsIgnoreCase(transactionType.type)) {
                    return transactionType;
                }
            }
        }
        throw new IllegalArgumentException("Invalid TransactionType value: " + value);
    }
}
