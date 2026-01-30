package com.city.storage;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileStorageService {

   private final String uploadDir = "uploads/logos/";

    public String saveProfileLogo(Long profileId, MultipartFile file) {

        try {
            Files.createDirectories(Paths.get(uploadDir));

            String filename = "profile_" + profileId + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);

            Files.write(path, file.getBytes());

            return "/uploads/logos/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el logo");
        }
    }
}