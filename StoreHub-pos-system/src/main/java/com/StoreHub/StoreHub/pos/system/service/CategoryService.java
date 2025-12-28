package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.payload.response.dto.CategoryDto;

import java.util.List;


public interface CategoryService {
    CategoryDto createCategoryDto(CategoryDto dto);
    List<CategoryDto> getCategoriesByStores(Long storeId);
    CategoryDto updateCate(Long id,CategoryDto dto);
    void deleteCategory(Long id);
}
