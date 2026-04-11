package com.StoreHub.StoreHub.pos.system.controller;

import com.StoreHub.StoreHub.pos.system.payload.dto.BillingDto;
import com.StoreHub.StoreHub.pos.system.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<BillingDto> generateBill(@PathVariable Long orderId) throws Exception {
        return ResponseEntity.ok(billingService.generateBill(orderId));
    }
}