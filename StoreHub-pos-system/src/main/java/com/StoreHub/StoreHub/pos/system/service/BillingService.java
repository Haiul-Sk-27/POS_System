package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.payload.dto.BillingDto;

public interface BillingService {
    BillingDto generateBill(Long orderId) throws Exception;
    void generateBillAndSendEmail(Long orderId) throws Exception;
}
