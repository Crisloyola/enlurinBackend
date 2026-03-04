package com.city.profile;

import com.city.files.FileStorageService;
import com.city.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;                          // ← NUEVO

@RestController
@RequestMapping("/profiles/me/media")
public class ProfileMediaController {

    private final ProfileMediaRepository mediaRepository;
    private final ProfileRepository profileRepository;
    private final FileStorageService fileStorageService;

    public ProfileMediaController(
            ProfileMediaRepository mediaRepository,
            ProfileRepository profileRepository,
            FileStorageService fileStorageService) {
        this.mediaRepository = mediaRepository;
        this.profileRepository = profileRepository;
        this.fileStorageService = fileStorageService;
    }

    // GET /profiles/me/media
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<ProfileMedia> getMyMedia(
            @AuthenticationPrincipal CustomUserDetails user) {
        Profile profile = profileRepository.findByUser_Email(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return mediaRepository.findByProfile_Id(profile.getId());
    }

    // POST /profiles/me/media → subir archivo (PHOTO)
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ProfileMedia> uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam(value = "title", required = false) String title,
            @AuthenticationPrincipal CustomUserDetails user) {

        Profile profile = profileRepository.findByUser_Email(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        String url = fileStorageService.saveMedia(profile.getId(), type, file);

        ProfileMedia media = new ProfileMedia();
        media.setProfile(profile);
        media.setType(ProfileMedia.MediaType.valueOf(type.toUpperCase()));
        media.setUrl(url);
        media.setTitle(title);

        return ResponseEntity.ok(mediaRepository.save(media));
    }

    // POST /profiles/me/media/link → guardar link de YouTube, TikTok, Facebook  ← NUEVO
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/link")
    public ResponseEntity<ProfileMedia> addVideoLink(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal CustomUserDetails user) {

        Profile profile = profileRepository.findByUser_Email(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        String url   = body.get("url");
        String type  = body.get("type");
        String title = body.get("title");

        if (url == null || url.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        ProfileMedia media = new ProfileMedia();
        media.setProfile(profile);
        media.setUrl(url);
        media.setTitle(title);

        try {
            media.setType(ProfileMedia.MediaType.valueOf(
                    type != null ? type.toUpperCase() : "VIDEO"
            ));
        } catch (IllegalArgumentException e) {
            media.setType(ProfileMedia.MediaType.VIDEO);
        }

        return ResponseEntity.ok(mediaRepository.save(media));
    }

    // DELETE /profiles/me/media/{mediaId}
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<?> deleteMedia(
            @PathVariable Long mediaId,
            @AuthenticationPrincipal CustomUserDetails user) {

        Profile profile = profileRepository.findByUser_Email(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        mediaRepository.deleteByProfile_IdAndId(profile.getId(), mediaId);
        return ResponseEntity.ok().build();
    }
}