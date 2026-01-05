package com.StoreHub.StoreHub.pos.system.repository;

import com.StoreHub.StoreHub.pos.system.model.Refund;
import com.StoreHub.StoreHub.pos.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund ,Long> {
    List<Refund> findByCashierIdAndCreateAtBetween(Long cashier,
                                                   LocalDateTime from,
                                                   LocalDateTime to);

    List<Refund> findByCashierId(Long id);
    List<Refund> findByShiftReportId(Long id);
    List<Refund> findByBranchId(Long id);
}
