package com.city.storage;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileStorageService {

    private static final String BASE_PATH = "uploads/profiles/";

    public static String saveProfileLogo(Long profileId, MultipartFile file) {
        try {
            String filename = "logo-" + System.currentTimeMillis() + ".png";
            Path dir = Paths.get(BASE_PATH + profileId);
            Files.createDirectories(dir);

            Path filePath = dir.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/profiles/" + profileId + "/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar imagen", e);
        }
    }
}