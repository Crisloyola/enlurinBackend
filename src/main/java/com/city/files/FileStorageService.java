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

    public String saveMedia(Long profileId, String type, MultipartFile file) {
    try {
            String folder = UPLOAD_DIR + "media/" + profileId + "/";
            Files.createDirectories(Paths.get(folder));
            String filename = type.toLowerCase() + "_" +
                            System.currentTimeMillis() + "_" +
                            file.getOriginalFilename();
            Files.write(Paths.get(folder + filename), file.getBytes());
            return "/uploads/media/" + profileId + "/" + filename;
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar archivo");
        }
    }
}