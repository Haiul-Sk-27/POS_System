package com.StoreHub.StoreHub.pos.system.controller;

import com.StoreHub.StoreHub.pos.system.payload.response.dto.ShiftReportDto;
import com.StoreHub.StoreHub.pos.system.service.ShiftReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shift-reports")
public class ShiftReportController {

    private final ShiftReportService shiftReportService;

    @PostMapping("/start")
    public ResponseEntity<ShiftReportDto> startShift() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.startShift()
        );
    }

    @PostMapping("/end")
    public ResponseEntity<ShiftReportDto> endShift() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.endShift(null,null)
        );
    }

    @PostMapping("/current")
    public ResponseEntity<ShiftReportDto> getCurrentShiftProgress() throws Exception {
        return ResponseEntity.ok(
                shiftReportService.getCurrentShiftProgress(null)
        );
    }

    @GetMapping("/cashier/{cashierId}/by-date")
    public ResponseEntity<ShiftReportDto> getShiftReportByDate(
            @PathVariable Long cashierId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDateTime date
    ) throws Exception {

        return ResponseEntity.ok(
                shiftReportService.getShiftByCashierAndDate(cashierId, date)
        );
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<ShiftReportDto>> getShiftReportByCashier(
            @PathVariable Long cashierId
    ) throws Exception {

        return ResponseEntity.ok(
                shiftReportService.getShiftReportsByCashierId(cashierId)
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ShiftReportDto>> getShiftReportByBranch(
            @PathVariable Long branchId
    ) throws Exception {

        return ResponseEntity.ok(
                shiftReportService.getShiftReportsByCashierId(branchId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftReportDto> getShiftReportById(
            @PathVariable Long id
    ) throws Exception {

        return ResponseEntity.ok(
                shiftReportService.getShiftReportById(id)
        );
    }

}
