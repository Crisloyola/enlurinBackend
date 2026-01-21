package com.city.auth;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.city.files.FileStorageService;
import com.city.profile.Profile;
import com.city.profile.ProfileRepository;
import com.city.user.User;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final FileStorageService fileStorageService;

    public ProfileController(ProfileRepository profileRepository, FileStorageService fileStorageService) {
        this.profileRepository = profileRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<Profile> search(
            @RequestParam String category,
            @RequestParam String district
    ) {
        return profileRepository
                .findByCategory_NameAndDistrict_Name(category, district);
    }

    @PostMapping("/profile/logo")
    public Profile uploadLogo(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user
    ) {
        Profile profile = profileRepository
                .findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        String logoUrl = fileStorageService.saveProfileLogo(profile.getId(), file);
        profile.setLogoUrl(logoUrl);

        return profileRepository.save(profile);
    }
    @PostMapping("/{id}/logo")
    public ResponseEntity<?> uploadLogo(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        String logoUrl = fileStorageService.saveProfileLogo(profile.getId(), file);
        profile.setLogoUrl(logoUrl);

        profileRepository.save(profile);

        return ResponseEntity.ok(profile);
    }
}