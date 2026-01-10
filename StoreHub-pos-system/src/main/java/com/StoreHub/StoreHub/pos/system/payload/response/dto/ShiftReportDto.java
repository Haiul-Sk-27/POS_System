package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import com.StoreHub.StoreHub.pos.system.model.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ShiftReportDto {
    private Long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Double totalSales;
    private Double totalRefunds;
    private Double netSale;
    private int totalOrders;
    private UserDto cashier;
    private Long cashierId;
    private Long branchId;
    private BranchDto branch;
    private List<PaymentSummary> paymentSummaries;
    private List<ProductDto> topSellingProducts;
    private List<OrderDto> recentOrder;
    private List<RefundDto> refunds;
}
