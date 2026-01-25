package com.city.files;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads/";

    public String saveProfileLogo(Long profileId, MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String filename = "profile_" + profileId + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + filename);

            Files.write(path, file.getBytes());

            return "/uploads/logos/" + filename;
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar archivo"
            );
        }
    }
}
