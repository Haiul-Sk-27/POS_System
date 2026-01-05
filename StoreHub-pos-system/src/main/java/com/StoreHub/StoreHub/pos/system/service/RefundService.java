package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.model.Refund;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.RefundDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {

    Refund createRefund(Refund refund) throws Exception;
    List<RefundDto> getAllRefunds() throws  Exception;
    List<RefundDto> getRefundByCashier(Long cashierId) throws Exception;
    List<RefundDto>  getRefundByShiftReport(Long shiftReportId) throws Exception;
    List<RefundDto> getRefundByCashierAndDateRange(Long cashierId,
                                                   LocalDateTime startDate,
                                                   LocalDateTime endDate) throws Exception;
    List<RefundDto> getRefundByBranch(Long branchId) throws Exception;
    RefundDto getRefundById(Long refundId) throws Exception;
    void deleteRefund(Long refundId) throws  Exception;
}
