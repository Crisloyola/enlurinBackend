package com.city.profile;

import com.city.category.Category;
import com.city.category.CategoryRepository;
import com.city.district.District;
import com.city.district.DistrictRepository;
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

    public Profile adminUpdateProfile(Long id, ProfileUpdateRequest req) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow();

        profile.setDescription(req.getDescription());
        return profileRepository.save(profile);
    }

    public Profile uploadLogo(Long id, MultipartFile file) {

        Profile profile = profileRepository.findById(id)
                .orElseThrow();

        String url = fileStorageService.saveProfileLogo(id, file);
        profile.setLogoUrl(url);

        return profileRepository.save(profile);
    }
}