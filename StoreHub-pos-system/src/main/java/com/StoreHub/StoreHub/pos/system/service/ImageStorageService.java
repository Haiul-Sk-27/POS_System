package com.StoreHub.StoreHub.pos.system.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {

    String saveProductImage(MultipartFile file) throws IOException;
}
