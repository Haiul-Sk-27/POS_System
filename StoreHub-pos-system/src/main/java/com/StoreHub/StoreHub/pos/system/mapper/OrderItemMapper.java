package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.OrderItem;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.OrderItemDto;

public class OrderItemMapper {
    public static OrderItemDto toDTO(OrderItem item){
        if(item== null) return  null;
        return OrderItemDto.builder()
                .id(item.getId())
                .product(ProductMapper.toDto(item.getProduct()))
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .product(ProductMapper.toDto(item.getProduct()))
                .build();
    }
}
