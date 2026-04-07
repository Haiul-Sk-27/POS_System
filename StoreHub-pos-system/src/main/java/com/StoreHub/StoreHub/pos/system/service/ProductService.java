package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(MultipartFile file,ProductDto productDto, User user) throws Exception;
    ProductDto updateProduct(Long id, ProductDto productDto,User user,MultipartFile file) throws Exception;
    void deleteProduct(Long id, User user) throws Exception;
    List<ProductDto> getProductByStoreId(Long storeId);
    List<ProductDto> searchByKeyword(Long storeId,String keyword);

}
