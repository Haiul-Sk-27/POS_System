package com.StoreHub.StoreHub.pos.system.utility;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorageUtil {
    private static final String BASE_DIR = "uploads/bills/";

    public static String savePdf(byte[] pdfBytes, String fileName) throws Exception {

        Path path = Path.of(BASE_DIR + fileName);
        // create folder if not exists
        Files.createDirectories(path.getParent());
        // write file
        Files.write(path, pdfBytes);

        return path.toAbsolutePath().toString();
    }
}
