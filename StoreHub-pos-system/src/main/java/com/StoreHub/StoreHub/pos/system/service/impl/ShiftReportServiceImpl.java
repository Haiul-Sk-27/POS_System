package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import com.StoreHub.StoreHub.pos.system.mapper.ShiftReportMapper;
import com.StoreHub.StoreHub.pos.system.model.*;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.ShiftReportDto;
import com.StoreHub.StoreHub.pos.system.repository.OrderRepository;
import com.StoreHub.StoreHub.pos.system.repository.RefundRepository;
import com.StoreHub.StoreHub.pos.system.repository.ShiftReportRepository;
import com.StoreHub.StoreHub.pos.system.repository.UserRepository;
import com.StoreHub.StoreHub.pos.system.service.ShiftReportService;
import com.StoreHub.StoreHub.pos.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserService userService;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ShiftReportDto startShift() throws Exception {
        User currentUser = userService.getCurrentUser();
        LocalDateTime shiftStart = LocalDateTime.now();
        LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existing = shiftReportRepository.findByCashierAndShiftStartBetween(currentUser,startOfDay,endOfDay);

        if(existing.isPresent()){
            throw new Exception("Shift alredy is start today");
        }

        Branch branch = currentUser.getBranch();

        ShiftReport shiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .shiftStart(shiftStart)
                .branch(branch)
                .build();

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        System.out.println("Shift:"+savedReport);

        return ShiftReportMapper.toDto(savedReport);
    }

    @Override
    public ShiftReportDto endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception {

        User currentUser = userService.getCurrentUser();
        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser).orElseThrow(
                ()->new Exception("Shirt is not found")
        );

        shiftReport.setShiftEnd(shiftEnd);

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(),shiftReport.getShiftStart(),shiftReport.getShiftEnd()
        );

        Double totalRefund = refunds.stream()
                .mapToDouble(refund -> refund.getAmount()!=null?refund.getAmount():0.0).sum();

        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser,shiftReport.getShiftStart(),shiftReport.getShiftEnd()
        );
        double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();

        int totalOrders = orders.size();

        double netSales = totalSales-totalRefund;

        shiftReport.setTotalRefunds(totalRefund);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSales);
        shiftReport.setRecentOrder(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(savedReport);
    }



    @Override
    public ShiftReportDto getShiftReportById(Long id) {
        return shiftReportRepository.findById(id).map(ShiftReportMapper::toDto).orElseThrow(
                ()->new ExpressionException("Shift report not found with given id:"+id)
        );

    }

    @Override
    public List<ShiftReportDto> getAllShipReports() {
        List<ShiftReport> reports = shiftReportRepository.findAll();
        return reports.stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportsByBranch(Long branchId) {
        List<ShiftReport> reports = shiftReportRepository.findByBranchId(branchId);

        return reports.stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportsByCashierId(Long cashierId) {
        List<ShiftReport> reports = shiftReportRepository.findByCashierId(cashierId);

        return reports.stream().map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception {

        User user = userService.getCurrentUser();
        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(user)
                .orElseThrow(()->new Exception("No extive shift found by chasher"));

        LocalDateTime now = LocalDateTime.now();
        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                user,shiftReport.getShiftStart(),now
        );

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                user.getId(),shiftReport.getShiftStart(),shiftReport.getShiftEnd()
        );

        Double totalRefund = refunds.stream()
                .mapToDouble(refund -> refund.getAmount()!=null?refund.getAmount():0.0).sum();

        double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();

        int totalOrders = orders.size();

        double netSales = totalSales-totalRefund;

        shiftReport.setTotalRefunds(totalRefund);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSales);
        shiftReport.setRecentOrder(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders,totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(savedReport);
    }

    @Override
    public ShiftReportDto getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {

        User cashier = userRepository.findById(cashierId).orElseThrow(
                ()->new Exception("cashier is not found with this id:"+cashierId)
        );

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(
                cashier,start,end
        ).orElseThrow(()->new Exception("Shift report not found for cashier"+cashier));

        return ShiftReportMapper.toDto(report);
    }

    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {

        Map<PaymentType, List<Order>> grouped = orders.stream()
                .collect(Collectors.groupingBy(
                        o -> Objects.requireNonNullElse(o.getPaymentType(), PaymentType.CASH)
                ));

        List<PaymentSummary> summaries = new ArrayList<>();

        for (Map.Entry<PaymentType, List<Order>> entry : grouped.entrySet()) {

            double amount = entry.getValue().stream()
                    .mapToDouble(o -> o.getTotalAmount() != null ? o.getTotalAmount() : 0.0)
                    .sum();

            int transactions = entry.getValue().size();
            double percentage = totalSales > 0 ? (amount / totalSales) * 100 : 0;

            PaymentSummary ps = new PaymentSummary();
            ps.setType(entry.getKey());
            ps.setTotalAmount(amount);
            ps.setTransactionCount(transactions);
            ps.setPercentage(percentage);

            summaries.add(ps);
        }

        return summaries;
    }


    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product,Integer> producrSalesMap = new HashMap<>();

        //p1 -5
        //p2 - 1
        //p3-4
        for(Order order:orders){
            for(OrderItem item: order.getItems()){
                Product product =item.getProduct();
                producrSalesMap.put(product,producrSalesMap.getOrDefault(product,0)+item.getQuantity());
            }

        }

        return producrSalesMap.entrySet().stream()
                .sorted((a,b)->b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Order> getRecentOrders(List<Order> orders) {
        return  orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
