package com.StoreHub.StoreHub.pos.system.payload.dto;

import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BillingDto {

    private Long orderId;
    private Long branchId;

    private Long customerId;
    private String customerName;
    private String customerEmail;

    private List<OrderItemDto> items;

    private double subTotal;
    private double taxAmount;
    private double discount;
    private double totalPayable;

    private PaymentType paymentType;
    private LocalDateTime billedAt;
}
