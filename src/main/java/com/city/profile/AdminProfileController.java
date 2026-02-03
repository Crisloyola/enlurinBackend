package com.city.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/profiles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProfileController {

    private final AdminProfileService adminProfileService;

    @GetMapping
    public List<Profile> listByStatus(@RequestParam ProfileStatus status) {
        return adminProfileService.listByStatus(status);
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
