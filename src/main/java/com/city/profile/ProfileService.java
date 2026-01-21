package com.city.profile;

import com.city.auth.dto.CreateProfileRequest;
import com.city.auth.dto.UpdateProfileRequest;
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

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DistrictRepository districtRepository;
    private final FileStorageService fileStorageService;

    // ======================
    // CREATE
    // ======================
    public Profile createProfile(
            String email,
            String businessName,
            String description,
            String phone,
            String address,
            String categorySlug,
            String districtSlug
    ) {

        if (profileRepository.findByUser_Email(email).isPresent()) {
            throw new RuntimeException("El usuario ya tiene un perfil");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Category category = categoryRepository.findBySlug(categorySlug)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        District district = districtRepository.findBySlug(districtSlug)
                .orElseThrow(() -> new RuntimeException("Distrito no encontrado"));

        Profile profile = new Profile();
        profile.setBusinessName(businessName);
        profile.setDescription(description);
        profile.setPhone(phone);
        profile.setAddress(address);
        profile.setCategory(category);
        profile.setDistrict(district);
        profile.setUser(user);
        profile.setSlug(SlugUtil.toSlug(businessName));

        return profileRepository.save(profile);
    }

    // ======================
    // UPDATE
    // ======================
    public Profile updateProfile(String email, ProfileUpdateRequest request) {

        Profile profile = profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        if (request.description() != null)
            profile.setDescription(request.description());

        if (request.phone() != null)
            profile.setPhone(request.phone());

        if (request.address() != null)
            profile.setAddress(request.address());

        if (request.categorySlug() != null) {
            Category category = categoryRepository.findBySlug(request.categorySlug())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            profile.setCategory(category);
        }

        if (request.districtSlug() != null) {
            District district = districtRepository.findBySlug(request.districtSlug())
                    .orElseThrow(() -> new RuntimeException("Distrito no encontrado"));
            profile.setDistrict(district);
        }

        return profileRepository.save(profile);
    }

    // ======================
    // UPLOAD LOGO
    // ======================
    public Profile uploadLogo(String email, MultipartFile file) {

        Profile profile = profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        String logoUrl = fileStorageService.saveProfileLogo(profile.getId(), file);
        profile.setLogoUrl(logoUrl);

        return profileRepository.save(profile);
    }
}
