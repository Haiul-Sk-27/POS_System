package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.domain.UserRole;
import com.StoreHub.StoreHub.pos.system.mapper.CategoryMapper;
import com.StoreHub.StoreHub.pos.system.model.Category;
import com.StoreHub.StoreHub.pos.system.model.Store;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.CategoryDto;
import com.StoreHub.StoreHub.pos.system.repository.CategoryRepository;
import com.StoreHub.StoreHub.pos.system.repository.StoreRepository;
import com.StoreHub.StoreHub.pos.system.service.CategoryService;
import com.StoreHub.StoreHub.pos.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final  StoreRepository storeRepository;
    @Override
    public CategoryDto createCategory(CategoryDto dto) throws Exception {
        User user = userService.getCurrentUser();
        Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(
                ()-> new Exception("Store not found")
        );

        Category category = Category.builder()
                .store(store)
                .name(dto.getName())
                .build();

        checkAuthority(user,category.getStore());

        return CategoryMapper.toDTO(categoryRepository.save(category));

    }

    @Override
    public List<CategoryDto> getCategoriesByStores(Long storeId) {

        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream()
                .map(
                        CategoryMapper::toDTO
                ).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto dto) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new Exception ("Category not found")
        );

        User user = userService.getCurrentUser();
        checkAuthority(user,category.getStore());
        category.setName(dto.getName());

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Category not found")
        );

        User user = userService.getCurrentUser();

        checkAuthority(user,category.getStore());

        categoryRepository.delete(category);
    }

    private  void checkAuthority(User user,Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager= user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if(!(isAdmin && isSameStore)&&!isManager){
            throw  new Exception("You don't have permission to manage this category");
        }
    }

}
