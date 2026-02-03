package com.city.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProfileService {

    private final ProfileRepository profileRepository;

    public Profile approve(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profile.setStatus(ProfileStatus.ACTIVE);
        return profileRepository.save(profile);
    }

    public Profile suspend(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profile.setStatus(ProfileStatus.INACTIVE);
        return profileRepository.save(profile);
    }

    public Profile toggleFeatured(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profile.setFeatured(!profile.isFeatured());
        return profileRepository.save(profile);
    }

    public List<Profile> listByStatus(ProfileStatus status) {
        return profileRepository.findByStatus(status);
    }
}
