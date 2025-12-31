package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private  String name;

//    private Store store;

    private Long storeId;
}
