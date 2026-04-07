package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.mapper.ProductMapper;
import com.StoreHub.StoreHub.pos.system.model.Category;
import com.StoreHub.StoreHub.pos.system.model.Product;
import com.StoreHub.StoreHub.pos.system.model.Store;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.dto.ProductDto;
import com.StoreHub.StoreHub.pos.system.repository.CategoryRepository;
import com.StoreHub.StoreHub.pos.system.repository.ProductRepository;
import com.StoreHub.StoreHub.pos.system.repository.StoreRepository;
import com.StoreHub.StoreHub.pos.system.service.FileStorageService;
import com.StoreHub.StoreHub.pos.system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private  final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ProductDto createProduct(
            MultipartFile file,
            ProductDto productDto,
            User user
    ) throws Exception {

        Store store = storeRepository.findById(productDto.getStoreId())
                .orElseThrow(() -> new Exception("Store not found"));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new Exception("Category ID not found"));

        String imagePath = fileStorageService.storeProductImage(file,productDto.getName());

        Product product = ProductMapper.toEntity(productDto, store, category);
        product.setImagePath(imagePath);
        product.setImageName(Paths.get(imagePath).getFileName().toString());

        return ProductMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(
            Long id,
            ProductDto productDto,
            User user,
            MultipartFile file
    ) throws Exception {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setMrp(productDto.getMrp());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setBrand(productDto.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new Exception("Category not found"));
            product.setCategory(category);
        }
        if (file != null && !file.isEmpty()) {
            String oldImagePath = product.getImagePath();
            String newImagePath =
                    fileStorageService.storeProductImage(file, productDto.getName());

            product.setImagePath(newImagePath);
            product.setImageName(Paths.get(newImagePath).getFileName().toString());
            if (oldImagePath != null) {
                try {
                    Files.deleteIfExists(Paths.get(oldImagePath));
                } catch (Exception e) {
                    System.err.println("Failed to delete old image: " + oldImagePath);
                }
            }
        }

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new Exception("Product not found")
        );
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getProductByStoreId(Long storeId) {
        List<Product> products =  productRepository.findByStoreId(storeId);

        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByKeyword(Long storeId, String keyword) {
        List<Product> products =  productRepository.SearchByKeyword(storeId,keyword);

        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
}
