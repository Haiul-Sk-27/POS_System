package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.domain.OrderStatus;
import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto) throws Exception;
    OrderDto getOrderById(Long id) throws  Exception;
    List<OrderDto> getOrderByBranch(Long branchId,
                                    Long customrId,
                                    Long cashierId,
                                    PaymentType paymentType,
                                    OrderStatus status) throws  Exception;
    List<OrderDto> getOrderByCashier(Long cashierId);
    void deleteOrder(Long id) throws Exception;
    List<OrderDto> getTodayOrderByBranch(Long branchId);
    List<OrderDto> getOrderByCustomerId(Long cutomerId);
    List<OrderDto> getTop5RecentOrdersByBranch(Long branchId )throws Exception;


}
