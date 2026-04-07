package com.StoreHub.StoreHub.pos.system.controller;

import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.ApiResponse;
import com.StoreHub.StoreHub.pos.system.payload.dto.ProductDto;
import com.StoreHub.StoreHub.pos.system.service.ProductService;
import com.StoreHub.StoreHub.pos.system.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> create(
            @RequestPart("product") String productJson,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.getUserFromJwtToken(jwt);

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);

        return ResponseEntity.ok(
                productService.createProduct(file, productDto, user)
        );
    }



    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDto>> getByStoreId(@PathVariable Long storeId,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(
                productService.getProductByStoreId(
                       storeId
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);

        productService.deleteProduct(
                id,
                user
        );

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product delte successfully");
        return ResponseEntity.ok(
            apiResponse
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.getUserFromJwtToken(jwt);

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);

        return ResponseEntity.ok(
                productService.updateProduct(
                        id,
                        productDto,
                        user,
                        file
                )
        );
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDto>> searchByKeyword(@PathVariable Long storeId,
                                                         @RequestParam String keyword,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(
                productService.searchByKeyword(
                        storeId,
                        keyword
                )
        );
    }
}
