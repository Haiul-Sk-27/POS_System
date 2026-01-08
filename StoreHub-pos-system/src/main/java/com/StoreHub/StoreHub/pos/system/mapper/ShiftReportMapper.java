package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.*;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.OrderDto;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.ProductDto;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.ShiftReportDto;

import java.security.cert.CertPathBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftReportMapper {

    public static ShiftReportDto toDto(ShiftReport entity){
        return ShiftReportDto.builder()
                .id(entity.getId())
                .shiftEnd(entity.getShiftEnd())
                .shiftStart(entity.getShiftStart())
                .totalSales(entity.getTotalSales())
                .totalOrders(entity.getTotalOrders())
                .netSale(entity.getNetSale())
                .cashier(UserMapper.toDTO(entity.getCashier()))
                .cashierId(entity.getCashier().getId())
                .branchId(entity.getBranch().getId())
                .recentOrder(mapOrders(entity.getRecentOrder()))
                .topSellingProducts(mapProducts(entity.getTopSellingProducts()))
                .paymentSummaries(entity.getPaymentSummaries())
                .build();


    }


//    private static List<RefundDto> mapRefunds(List<Refund> refunds) {
//        if (refunds == null || refunds.isEmpty()) return List.of();
//        return refunds.stream()
//                .map(RefundMapper::toDTO)
//                .collect(Collectors.toList());
//    }


    private static List<ProductDto> mapProducts(List<Product> topSellingProducts) {
        if(topSellingProducts==null||topSellingProducts.isEmpty()){return null;}
        return topSellingProducts.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    private static List<OrderDto> mapOrders(List<Order> recentOrder) {
        if(recentOrder ==null||recentOrder.isEmpty()){return null;}
        return recentOrder.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }
}


