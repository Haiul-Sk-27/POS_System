package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.payload.response.dto.ShiftReportDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {
    ShiftReportDto startShift() throws Exception;
    ShiftReportDto endShift(Long shiftReportId,LocalDateTime shiftEnd)throws Exception;
    ShiftReportDto getShiftReportById(Long id);
    List<ShiftReportDto> getAllShipReports();
    List<ShiftReportDto> getShiftReportsByBranch(Long branchId);
    List<ShiftReportDto> getShiftReportsByCashierId(Long cashierId);
    ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception;
    ShiftReportDto getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception;
}
