package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.Category;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toDTO(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .id(category.getId())
                .storeId(category.getStore() != null?category.getStore().getId():null)
                .build();
    }
}
