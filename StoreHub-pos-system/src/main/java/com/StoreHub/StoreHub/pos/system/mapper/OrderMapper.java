package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.Order;
import com.StoreHub.StoreHub.pos.system.payload.dto.OrderDto;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto toDTO(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .branchId(order.getBranch().getId())
                .cashier(UserMapper.toDTO(order.getCashier()))
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer() != null ? order.getCustomer().getFullName() : null)
                .paymentType(order.getPaymentType())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(OrderItemMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
