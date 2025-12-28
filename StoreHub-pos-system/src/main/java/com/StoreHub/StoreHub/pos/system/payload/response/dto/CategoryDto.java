package com.StoreHub.StoreHub.pos.system.payload.response.dto;

import com.StoreHub.StoreHub.pos.system.model.Store;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class CategoryDto {

    private Long id;

    private  String name;

    private Store store;
}
