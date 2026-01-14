package com.StoreHub.StoreHub.pos.system.mapper;

import com.StoreHub.StoreHub.pos.system.model.Category;
import com.StoreHub.StoreHub.pos.system.model.Product;
import com.StoreHub.StoreHub.pos.system.model.Store;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.ProductDto;

public class ProductMapper {

    public static ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .categoryId(product.getCategory()!=null?product.getCategory().getId():null)
                .category(CategoryMapper.toDTO(product.getCategory()))
                .storeId(product.getStore()!= null?product.getStore().getId():null)
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static  Product toEntity(ProductDto productDto, Store store, Category category){
        return Product.builder()
                .name(productDto.getName())
                .sku(productDto.getSku())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .sellingPrice(productDto.getSellingPrice())
                .brand(productDto.getBrand())
                .image(productDto.getImage())
                .store(store)
                .category(category)
                .build();
    }
}
