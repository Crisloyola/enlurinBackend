package com.city.auth;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.city.profile.Profile;
import com.city.profile.PublicProfileService;

public class PublicProfileController {

    private final PublicProfileService service;

     public PublicProfileController(PublicProfileService service) {
        this.service = service;
    }

    // 🔥 LISTADO
    @GetMapping("/{category}/{district}")
    public List<Profile> list(
            @PathVariable String category,
            @PathVariable String district
    ) {
        return service.listProfiles(category, district);
    }

    // 🔥 DETALLE
    @GetMapping("/{category}/{district}/{slug}")
    public Profile detail(
            @PathVariable String category,
            @PathVariable String district,
            @PathVariable String slug
    ) {
        return service.getProfile(category, district, slug);
    }
}
