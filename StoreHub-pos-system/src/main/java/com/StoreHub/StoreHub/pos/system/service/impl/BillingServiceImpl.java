package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.payload.dto.BillingDto;
import com.StoreHub.StoreHub.pos.system.payload.dto.OrderDto;
import com.StoreHub.StoreHub.pos.system.payload.dto.OrderItemDto;
import com.StoreHub.StoreHub.pos.system.service.BillingService;
import com.StoreHub.StoreHub.pos.system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final OrderService orderService;
    private static final double GST_PERCENT = 18.0;
    @Override
    public BillingDto generateBill(Long orderId) throws Exception {
        OrderDto order = orderService.getOrderById(orderId);
        List<OrderItemDto> items = order.getItems();

        double subTotal = calculateSubtotal(items);
        double taxAmount = calculateTax(subTotal);
        double discount = calculateDiscount(subTotal);

        double totalPayable = subTotal + taxAmount - discount;

        return BillingDto.builder()
                .orderId(order.getId())
                .branchId(order.getBranchId())
                .customerId(order.getCustomerId())
                .customerName(order.getCustomerName() != null ? order.getCustomerName() : null)
                .items(items)
                .subTotal(subTotal)
                .taxAmount(taxAmount)
                .discount(discount)
                .totalPayable(totalPayable)
                .paymentType(order.getPaymentType())
                .billedAt(LocalDateTime.now())
                .build();
    }

    private double calculateSubtotal(List<OrderItemDto> items){
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    private double calculateTax(double subTotal) {
        return (subTotal * GST_PERCENT) / 100;
    }

    private double calculateDiscount(double subTotal) {
        if (subTotal >= 5000) {
            return subTotal * 0.10;
        }
        if (subTotal >= 2000) {
            return subTotal * 0.05;
        }
        return 0;
    }
}
