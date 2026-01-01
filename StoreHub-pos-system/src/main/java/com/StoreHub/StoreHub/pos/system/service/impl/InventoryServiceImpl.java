package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.mapper.InventoryMapper;
import com.StoreHub.StoreHub.pos.system.model.Branch;
import com.StoreHub.StoreHub.pos.system.model.Inventory;
import com.StoreHub.StoreHub.pos.system.model.Product;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.InventoryDto;
import com.StoreHub.StoreHub.pos.system.repository.BranchRepository;
import com.StoreHub.StoreHub.pos.system.repository.InventoryRepository;
import com.StoreHub.StoreHub.pos.system.repository.ProductRepository;
import com.StoreHub.StoreHub.pos.system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private  final ProductRepository productRepository;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {

        Branch branch = branchRepository.findById(inventoryDto.getBranchId()).orElseThrow(
                ()->new Exception("Branch not existing")
        );

        Product product = productRepository.findById(inventoryDto.getProductId()).orElseThrow(
                ()-> new Exception("product not exist")
        );

        Inventory inventory = InventoryMapper.toEntity(inventoryDto,branch,product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(savedInventory);
    }

    @Override
    public InventoryDto updateInventory( Long id,InventoryDto inventoryDto) {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()->new Exception("Inventory not found")
        );

        inventory.setQuantity(inventoryDto.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) {

    }

    @Override
    public InventoryDto getInventoryById(Long id) {
        return null;
    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId) {
        return null;
    }

    @Override
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId) {
        return List.of();
    }
}
