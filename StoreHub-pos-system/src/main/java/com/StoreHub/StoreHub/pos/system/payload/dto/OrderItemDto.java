package com.StoreHub.StoreHub.pos.system.payload.dto;

import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
