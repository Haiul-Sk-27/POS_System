package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.domain.OrderStatus;
import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import com.StoreHub.StoreHub.pos.system.mapper.OrderMapper;
import com.StoreHub.StoreHub.pos.system.model.*;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.OrderDto;
import com.StoreHub.StoreHub.pos.system.repository.CustomerRepository;
import com.StoreHub.StoreHub.pos.system.repository.OrderRepository;
import com.StoreHub.StoreHub.pos.system.repository.ProductRepository;
import com.StoreHub.StoreHub.pos.system.service.OrderService;
import com.StoreHub.StoreHub.pos.system.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private  final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) throws Exception {

        User cashier = userService.getCurrentUser();
        Branch branch = cashier.getBranch();
        if(branch == null){
            throw new Exception("Caisher's branch not found");
        }

        Customer customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Order order = Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(customer)
                .paymentType(orderDto.getPaymentType())
                .build();

        List<OrderItem> orderItems = orderDto.getItems().stream().map(
                itemDto ->{
                    Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(
                            ()-> new EntityNotFoundException("Product not foud")
                    );

                    return OrderItem.builder()
                            .product(product)
                            .quantity(itemDto.getQuantity())
                            .price(product.getSellingPrice() * itemDto.getQuantity())
                            .order(order)
                            .build();
                }
        ).toList();

        double total = orderItems.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();


        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) throws Exception {
        return orderRepository.findById(id)
                .map(OrderMapper::toDTO)
                .orElseThrow(
                ()->new Exception("Order not found"+id)
        );
    }

    @Override
    public List<OrderDto> getOrderByBranch(Long branchId, Long customrId, Long cashierId, PaymentType paymentType, OrderStatus status) throws Exception {
        return orderRepository.findByBranchId(branchId).stream()
                .filter(order -> customrId==null||
                        (order.getCustomer()!=null&&order.getCustomer().getId().equals(customrId)))
                .filter(order -> cashierId==null||
                        order.getCashier() !=null&&
                        order.getCashier().getId().equals(cashierId))
                .filter(order -> paymentType == null||order.getPaymentType()==paymentType)
                .map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrderByCashier(Long cashierId) {
        return orderRepository.findByCashierId(cashierId).stream()
                .map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(
                ()->new Exception("Order not found with id"+id)
        );
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getTodayOrderByBranch(Long branchId) {

        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        return orderRepository.findByBranchIdAndCreatedAtBetween(
                branchId,start,end
        ).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrderByCustomerId(Long cutomerId) {
        return orderRepository.findByCustomerId(cutomerId).stream().map(
                OrderMapper::toDTO
        ).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getTop5RecentOrdersByBranch(Long branchId) throws Exception {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId).stream()
                .map(
                        OrderMapper::toDTO
                ).collect(Collectors.toList());
    }
}
