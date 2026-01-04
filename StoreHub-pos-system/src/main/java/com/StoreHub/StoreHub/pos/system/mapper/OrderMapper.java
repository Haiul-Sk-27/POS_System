package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.Order;
import com.StoreHub.StoreHub.pos.system.model.OrderItem;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.OrderDto;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto toDTO(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .branchId(order.getBranch().getId())
                .cashier(UserMapper.toDTO(order.getCashier()))
                .customerId(order.getCustomer().getId())
                .paymentType(order.getPaymentType())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(OrderItemMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
