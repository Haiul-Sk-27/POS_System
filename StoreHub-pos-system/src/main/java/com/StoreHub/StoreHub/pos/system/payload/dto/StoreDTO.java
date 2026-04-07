package com.StoreHub.StoreHub.pos.system.payload.dto;

import com.StoreHub.StoreHub.pos.system.domain.StoreStatus;
import com.StoreHub.StoreHub.pos.system.model.StoreContact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {

    private Long id;

    private String brand;

    private UserDto storeAdmin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String description;
    private String  storeType;

    private StoreStatus status;

    private StoreContact contact;
}
