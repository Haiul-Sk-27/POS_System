package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.mapper.RefundMapper;
import com.StoreHub.StoreHub.pos.system.model.*;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.RefundDto;
import com.StoreHub.StoreHub.pos.system.repository.OrderRepository;
import com.StoreHub.StoreHub.pos.system.repository.RefundRepository;
import com.StoreHub.StoreHub.pos.system.repository.ShiftReportRepository;
import com.StoreHub.StoreHub.pos.system.service.RefundService;
import com.StoreHub.StoreHub.pos.system.service.ShiftReportService;
import com.StoreHub.StoreHub.pos.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;
    private  final ShiftReportRepository shiftReportRepository;

    @Override
    public RefundDto createRefund(RefundDto refundDto) throws Exception {

        User cashier = userService.getCurrentUser();

        Order order = orderRepository.findById(refundDto.getOrderId()).orElseThrow(
                ()->new Exception("Order not found")
        );

       ShiftReport shiftReport = shiftReportRepository
               .findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(cashier)
               .orElseThrow(() -> new IllegalStateException("No active shift for this cashier"));

        Branch branch =order.getBranch();
        Refund createRefund = Refund.builder()
                .order(order)
                .cashier(cashier)
                .branch(branch)
                .shiftReport(shiftReport)
                .reason(refundDto.getReason())
                .amount(refundDto.getAmount())
                .paymentType(refundDto.getPaymentType())
                .build();

        Refund savedRefund = refundRepository.save(createRefund);

        return RefundMapper.toDTO(savedRefund);
    }

    @Override
    public List<RefundDto> getAllRefunds() throws Exception {

        return refundRepository.findAll().stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto>  getRefundByCashier(Long cashierId) throws Exception {
        return refundRepository.findByCashierId(cashierId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByShiftReport(Long shiftReportId) throws Exception {
        return refundRepository.findByShiftReportId(shiftReportId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        return refundRepository.findByCashierIdAndCreatedAtBetween(
                cashierId,startDate,endDate
        ).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByBranch(Long branchId) throws Exception {
        return refundRepository.findByBranchId(branchId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RefundDto getRefundById(Long refundId) throws Exception {
        return refundRepository.findById(refundId).map(RefundMapper::toDTO).orElseThrow(
                ()->new Exception("Refund not found")
        );
    }

    @Override
    public void deleteRefund(Long refundId) throws Exception {

        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new Exception("Refund not found"));

        refundRepository.delete(refund);
    }
}
