package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import com.StoreHub.StoreHub.pos.system.model.Order;
import com.StoreHub.StoreHub.pos.system.model.Product;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {

    private Long id;

    private Integer quantity;

    private Double price;

    @ManyToOne
    private ProductDto product;

    private Long productId;

    private Long orderId;
}
