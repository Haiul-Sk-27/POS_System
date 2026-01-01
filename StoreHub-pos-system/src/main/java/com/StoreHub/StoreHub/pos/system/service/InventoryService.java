package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.payload.response.dto.InventoryDto;

import java.util.List;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto inventoryDto) throws Exception;
    InventoryDto updateInventory(Long id,InventoryDto inventoryDto);
    void deleteInventory(Long id);
    InventoryDto getInventoryById(Long id);
    InventoryDto getInventoryByProductIdAndBranchId(Long productId);
    List<InventoryDto> getAllInventoryByBranchId(Long branchId);
}
