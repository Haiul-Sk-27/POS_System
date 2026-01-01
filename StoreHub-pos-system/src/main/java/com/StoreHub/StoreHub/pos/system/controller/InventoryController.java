package com.StoreHub.StoreHub.pos.system.controller;

import com.StoreHub.StoreHub.pos.system.payload.response.ApiResponse;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.InventoryDto;
import com.StoreHub.StoreHub.pos.system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {

    private  final InventoryService inventoryService;

    @PostMapping()
    public ResponseEntity<InventoryDto> create(@RequestBody  InventoryDto inventoryDto) throws Exception {
        return  ResponseEntity.ok(inventoryService.createInventory(inventoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> update(@RequestBody  InventoryDto inventoryDto,
                                               @PathVariable Long id) throws Exception {
        return  ResponseEntity.ok(inventoryService.updateInventory(id,inventoryDto));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<ApiResponse> delete(@PathVariable Long id) throws Exception {

        inventoryService.deleteInventory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Delte Inventory");
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<List<InventoryDto>> getInventoryByBranch(@PathVariable Long id){

    }
}
