package com.city.profile;

import org.springframework.lang.NonNull;
import com.city.files.FileStorageService;
import com.city.user.User;
import com.city.user.UserRepository;
import com.city.utils.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.city.profile.dto.ProfileCreateRequest;
import com.city.profile.dto.ProfileUpdateRequest;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

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
                .orElseThrow();

        profile.setDescription(req.getDescription());
        return profileRepository.save(profile);
    }

    public Profile uploadLogo(@NonNull Long id, MultipartFile file) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow();

        String url = fileStorageService.saveProfileLogo(id, file);
        profile.setLogoUrl(url);

        return profileRepository.save(profile);
    }

    public Profile deleteProfile(@NonNull Long id) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profileRepository.delete(profile);
        return profile;
    }
}