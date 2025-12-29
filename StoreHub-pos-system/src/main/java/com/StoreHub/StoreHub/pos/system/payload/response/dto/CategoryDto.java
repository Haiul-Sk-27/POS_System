package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private Long id;

    private  String name;

//    private Store store;

    private Long storeId;
}
