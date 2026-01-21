package com.city.profile;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/negocio")
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
