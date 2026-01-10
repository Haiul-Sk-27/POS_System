package com.StoreHub.StoreHub.pos.system.repository;

import com.StoreHub.StoreHub.pos.system.model.ShiftReport;
import com.StoreHub.StoreHub.pos.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShiftReportRepository extends JpaRepository<ShiftReport,Long> {
    List<ShiftReport> findByCashierId(Long id);
    List<ShiftReport> findByBranchId(Long id);
    Optional<ShiftReport> findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(User cashier);
    Optional<ShiftReport> findByCashierAndShiftStartBetWeen(User cashier,
                                                            LocalDateTime start,
                                                            LocalDateTime end);
}
