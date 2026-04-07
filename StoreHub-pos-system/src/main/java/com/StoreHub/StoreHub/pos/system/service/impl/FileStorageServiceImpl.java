package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.exceptions.FileStorageException;
import com.StoreHub.StoreHub.pos.system.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Path UPLOAD_DIR = Paths.get("uploads/product_image");

    public FileStorageServiceImpl() {
        try {
            Files.createDirectories(UPLOAD_DIR);
        } catch (IOException e) {
            throw new FileStorageException("Could not initialize upload directory", e);
        }
    }

    @Override
    public String storeProductImage(MultipartFile file) throws FileStorageException {

        if (file.isEmpty()) {
            throw new FileStorageException("File is empty");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new FileStorageException("Only image files are allowed");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            Path targetPath = UPLOAD_DIR.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file"+ e);
        }
    }
}
