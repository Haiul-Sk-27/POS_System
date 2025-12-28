package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.payload.response.dto.CategoryDto;
import com.StoreHub.StoreHub.pos.system.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryDto createCategoryDto(CategoryDto dto) {
        return null;
    }

    @Override
    public List<CategoryDto> getCategoriesByStores(Long storeId) {
        return List.of();
    }

    @Override
    public CategoryDto updateCate(Long id, CategoryDto dto) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }
}
