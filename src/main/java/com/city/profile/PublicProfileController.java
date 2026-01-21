package com.city.profile;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Negocios Públicos", description = "Endpoints públicos SEO")
@RestController
@RequestMapping("/negocio")
public class PublicProfileController {

    private final PublicProfileService service;

    public PublicProfileController(PublicProfileService service) {
        this.service = service;
    }

    // 🔥 LISTADO SEO
    // /negocio/restaurantes/lima
    @GetMapping("/{category}/{district}")
    public List<Profile> list(
            @PathVariable String category,
            @PathVariable String district
    ) {
        return service.listProfiles(category, district);
    }

    // 🔥 DETALLE SEO
    // /negocio/restaurantes/lima/pizzeria-don-pepe
    @GetMapping("/{category}/{district}/{slug}")
    public Profile detail(
            @PathVariable String category,
            @PathVariable String district,
            @PathVariable String slug
    ) {
        return service.getProfile(category, district, slug);
    }
}
