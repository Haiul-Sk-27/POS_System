package com.StoreHub.StoreHub.pos.system.model;

import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import lombok.Data;

@Data
public class PaymentSummary {

    private PaymentType type;
    private Double totalAmount;
    private int transactionCount;
    private Double percentage;
}
