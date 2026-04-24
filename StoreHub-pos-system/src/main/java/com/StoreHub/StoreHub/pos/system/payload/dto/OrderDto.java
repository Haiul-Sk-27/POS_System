package com.StoreHub.StoreHub.pos.system.payload.dto;

import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import com.StoreHub.StoreHub.pos.system.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private Long branchId;

    private Long customerId;

    private BranchDto branch;

    private UserDto cashier;
    private String customerName;
    private Customer customer;
    private String customerEmail;

    private PaymentType paymentType;

    private List<OrderItemDto> items;
}
