package com.city.profile;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProfileService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminProfileService.class);
    private final ProfileRepository profileRepository;

    public AdminProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile approve(Long id) {
        log.info("approve() called with id={}", id);
        java.util.Optional<Profile> opt = profileRepository.findById(id);
        log.info("findById result present={}", opt.isPresent());
        Profile profile = opt.orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Perfil no encontrado"
                ));

        profile.setStatus(ProfileStatus.ACTIVE);
        Profile saved = profileRepository.save(profile);
        log.info("profile {} approved, status={}", saved.getId(), saved.getStatus());
        return saved;
    }

    public Profile suspend(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Perfil no encontrado"
                ));

        profile.setStatus(ProfileStatus.INACTIVE);
        return profileRepository.save(profile);
    }

    public Profile toggleFeatured(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Perfil no encontrado"
                ));

        profile.setFeatured(!profile.isFeatured());
        return profileRepository.save(profile);
    }

    public List<Profile> listByStatus(ProfileStatus status) {
        return profileRepository.findByStatus(status);
    }

    /**
     * utility para obtener un perfil por id (comportamiento de 404)
     * usado principalmente por el frontend para depurar.
     */
    public Profile findById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Perfil no encontrado"
                ));
    }
}
