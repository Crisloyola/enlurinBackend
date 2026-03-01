package com.city.files;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + "/uploads/";

    public String saveProfileLogo(Long profileId, MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String filename = "logo_" + profileId + "_" + file.getOriginalFilename();
            Files.write(Paths.get(UPLOAD_DIR + filename), file.getBytes());
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar logo");
        }
    }

    public String saveBanner(Long profileId, MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String filename = "banner_" + profileId + "_" + file.getOriginalFilename();
            Files.write(Paths.get(UPLOAD_DIR + filename), file.getBytes());
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar banner");
        }
    }
}