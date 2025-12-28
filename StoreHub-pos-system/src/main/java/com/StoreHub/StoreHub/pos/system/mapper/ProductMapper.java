package com.StoreHub.StoreHub.pos.system.mapper;

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
                .storeId(product.getStore()!= null?product.getStore().getId():null)
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static  Product toEntity(ProductDto productDto, Store store){
        return Product.builder()
                .name(productDto.getName())
                .sku(productDto.getSku())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .brand(productDto.getBrand())
                .image(productDto.getImage())
                .store(store)
                .build();
    }
}
