package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import com.StoreHub.StoreHub.pos.system.model.Branch;
import com.StoreHub.StoreHub.pos.system.model.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDto {

    private Long id;

    private Long branchId;
    private Long productId;

    private ProductDto product;

    private BranchDto branch;

    private  Integer quantity;

    private LocalDateTime lastUpdate;
}
