package com.city.profile;

import com.city.category.Category;
import com.city.category.CategoryRepository;
import com.city.district.District;
import com.city.district.DistrictRepository;
import com.city.user.User;
import com.city.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DistrictRepository districtRepository;

    public Profile createProfile(
            Long userId,
            String businessName,
            String description,
            String phone,
            String address,
            String categorySlug,
            String districtSlug
    ) {

        if (profileRepository.findByUser_Id(userId).isPresent()) {
            throw new RuntimeException("El usuario ya tiene un perfil");
        }

        User user = userRepository.findById(userId)
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

        // 🔑 generar slug SEO
        profile.setSlug(generateSlug(businessName));

        return profileRepository.save(profile);
    }

    private String generateSlug(String text) {
        return text
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
