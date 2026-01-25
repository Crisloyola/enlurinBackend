package com.city.profile;

import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import com.city.files.FileStorageService;
import com.city.user.User;
import com.city.user.UserRepository;
import com.city.utils.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.city.profile.dto.ProfileCreateRequest;

import com.city.profile.dto.ProfileUpdateRequest;



@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public ProfileService(
            ProfileRepository profileRepository,
            UserRepository userRepository,
            FileStorageService fileStorageService
    ) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }


    public Profile createProfile(String email, ProfileCreateRequest req) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        if (profileRepository.findByUser_Id(user.getId()).isPresent()) {
            throw new RuntimeException("Ya tienes un perfil");
        }

        Profile profile = new Profile();
        profile.setBusinessName(req.getBusinessName());
        profile.setSlug(SlugUtil.toSlug(req.getBusinessName()));
        profile.setUser(user);

        return profileRepository.save(profile);
    }

    public Profile updateProfile(String email, ProfileUpdateRequest req) {

        Profile profile = profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profile.setDescription(req.getDescription());
        profile.setPhone(req.getPhone());
        profile.setAddress(req.getAddress());

        return profileRepository.save(profile);
    }

    public Profile adminUpdateProfile(@NonNull Long id, ProfileUpdateRequest req) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profile.setDescription(req.getDescription());
        return profileRepository.save(profile);
    }

    public Profile uploadLogo(@NonNull Long id, @NonNull String email, @NonNull MultipartFile file) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        if (!profile.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No eres dueño de este perfil");
        }

        String url = fileStorageService.saveProfileLogo(id, file);
        profile.setLogoUrl(url);

        return profileRepository.save(profile);
    }

    public Profile deleteProfile(@NonNull Long id) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profileRepository.delete( profile);

        return profile;
    }


    public Profile getBySlug(String slug) {
    return profileRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    public Profile getMyProfile(String email) {
    return profileRepository.findByUser_Email(email)
            .orElseThrow(() -> new RuntimeException("El usuario no tiene perfil"));
    }

    public Profile getByEmail(String email){
        return profileRepository.findByUser_Email(email)
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    public Profile getProfileBySlug(String slug) {
    return profileRepository.findBySlug(slug)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Perfil no encontrado"
                    )
            );
    }
}