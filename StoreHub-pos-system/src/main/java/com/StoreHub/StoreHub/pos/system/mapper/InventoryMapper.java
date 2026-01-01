package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.Branch;
import com.StoreHub.StoreHub.pos.system.model.Inventory;
import com.StoreHub.StoreHub.pos.system.model.Product;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.InventoryDto;

public class InventoryMapper {

    public static InventoryDto toDTO(Inventory inventory) {
        return InventoryDto.builder()
                .id(inventory.getId())
                .branchId(inventory.getBranch().getId())
                .productId(inventory.getProduct().getId())
                .product(ProductMapper.toDto(inventory.getProduct()))
                .branch(BranchMapper.toDTO(inventory.getBranch()))
                .quantity(inventory.getQuantity())
                .lastUpdate(inventory.getLastUpdate())
                .build();
    }

    public  static Inventory toEntity(InventoryDto inventoryDto,
                                      Branch branch, Product product){
        return Inventory.builder()
                .branch(branch)
                .product(product)
                .quantity(inventoryDto.getQuantity())
                .build();
    }
}
