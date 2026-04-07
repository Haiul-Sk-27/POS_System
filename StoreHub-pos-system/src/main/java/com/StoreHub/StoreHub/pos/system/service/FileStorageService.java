package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.exceptions.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeProductImage(MultipartFile file,String productName) throws FileStorageException;
}
