package com.city.auth;
import org.springframework.web.bind.annotation.*;

import com.city.profile.Profile;
import com.city.profile.ProfileRepository;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public List<Profile> search(
            @RequestParam String category,
            @RequestParam String district
    ) {
        return profileRepository
                .findByCategory_NameAndDistrict_Name(category, district);
    }
}