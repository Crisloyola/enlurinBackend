package com.city.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveProfileLogo(Long profileId, MultipartFile file) {

        try {
            String filename = "logo." + getExtension(file.getOriginalFilename());

            Path profileDir = Paths.get(uploadDir, "profiles", profileId.toString());
            Files.createDirectories(profileDir);

            Path filePath = profileDir.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/profiles/" + profileId + "/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Error al subir imagen");
        }
    }

    private String getExtension(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }
}