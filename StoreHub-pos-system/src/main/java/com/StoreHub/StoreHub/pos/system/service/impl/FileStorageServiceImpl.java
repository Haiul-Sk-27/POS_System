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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Path UPLOAD_DIR =
            Paths.get(System.getProperty("user.dir"), "uploads", "product_image");

    public FileStorageServiceImpl() throws FileStorageException {
        try {
            Files.createDirectories(UPLOAD_DIR);
        } catch (IOException e) {
            throw new FileStorageException("Could not initialize upload directory", e);
        }
    }

    @Override
    public String storeProductImage(MultipartFile file, String productName) throws FileStorageException {

        if (file == null || file.isEmpty()) {
            throw new FileStorageException("File is empty");
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new FileStorageException("Only image files are allowed");
        }

        String cleanProductName = productName
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "_");

        String timeStamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : "";

        String fileName = cleanProductName + "_" + timeStamp + extension;

        try {
            Path targetPath = UPLOAD_DIR.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // ✅ IMPORTANT
            return "/uploads/product_image/" + fileName;

        } catch (IOException e) {
            throw new FileStorageException("Failed to store file", e);
        }
    }
}