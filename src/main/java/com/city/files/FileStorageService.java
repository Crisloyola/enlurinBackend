package com.city.files;

import java.io.IOException;
import java.nio.file.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/";

    public String saveProfileLogo(Long profileId, MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String filename = "profile_" + profileId + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + filename);

            Files.write(path, file.getBytes());

            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }
}
