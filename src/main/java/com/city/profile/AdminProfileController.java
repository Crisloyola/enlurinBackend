package com.city.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.city.profile.dto.ProfilePublicResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/profiles")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProfileController {

    private final AdminProfileService adminProfileService;

    public AdminProfileController(AdminProfileService adminProfileService) {
        this.adminProfileService = adminProfileService;
    }

    @GetMapping
    public ResponseEntity<?> listByStatus(
        @RequestParam(required = false) String status
    ) {
        try {
            ProfileStatus profileStatus = ProfileStatus.PENDING;
            if (status != null && !status.isBlank()) {
                profileStatus = ProfileStatus.valueOf(status.trim().toUpperCase());
            }
            List<ProfilePublicResponse> result = adminProfileService.listByStatus(profileStatus)
                .stream()
                .map(ProfilePublicResponse::from)
                .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Esto nos dirá exactamente qué falla
            return ResponseEntity.badRequest().body(
                "Error: " + e.getClass().getName() + " — " + e.getMessage()
            );
        }
    }

    @GetMapping("/{id}")
    public Profile get(@PathVariable Long id) {
        return adminProfileService.findById(id);
    }

    @PutMapping("/{id}/approve")
    public Profile approve(@PathVariable Long id) {
        return adminProfileService.approve(id);
    }

    @PutMapping("/{id}/suspend")
    public Profile suspend(@PathVariable Long id) {
        return adminProfileService.suspend(id);
    }

    @PutMapping("/{id}/featured")
    public Profile toggleFeatured(@PathVariable Long id) {
        return adminProfileService.toggleFeatured(id);
    }
}