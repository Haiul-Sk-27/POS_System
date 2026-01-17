package com.StoreHub.StoreHub.pos.system.model;

import com.StoreHub.StoreHub.pos.system.domain.PaymentType;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.OrderDto;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Refund {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String reason;
    private Double amount;

    @ManyToOne
    @JsonIgnore
    private ShiftReport shiftReport;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
