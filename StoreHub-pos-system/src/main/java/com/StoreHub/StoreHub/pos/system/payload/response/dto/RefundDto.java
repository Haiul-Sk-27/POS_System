package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import com.StoreHub.StoreHub.pos.system.model.Branch;
import com.StoreHub.StoreHub.pos.system.model.Order;
import com.StoreHub.StoreHub.pos.system.model.ShiftReport;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDto {

    private  Long id;

//    private SpringDataJaxb.OrderDto order;
    private Long orderId;

    private String reason;

    private Double amount;

//    private ShiftReport shiftReport;

    private Long shiftReportId;

    private UserDto cashier;
    private String cashierName;

    private BranchDto branch;
    private Long branchId;

    private PaymentType paymentType;
    private LocalDateTime createdAt;
}