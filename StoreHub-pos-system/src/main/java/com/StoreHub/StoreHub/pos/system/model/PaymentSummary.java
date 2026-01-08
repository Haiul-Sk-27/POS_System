package com.StoreHub.StoreHub.pos.system.model;

import com.StoreHub.StoreHub.pos.system.domain.PaymentType;

public class PaymentSummary {

    private PaymentType type;
    private Double totalAmount;
    private int transactionCount;
    private Double percentage;
}
