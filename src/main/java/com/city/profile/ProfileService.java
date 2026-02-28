package com.city.profile;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import com.city.category.CategoryRepository;
import com.city.district.DistrictRepository;
import com.city.files.FileStorageService;
import com.city.user.User;
import com.city.user.UserRepository;
import com.city.utils.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.city.profile.dto.ProfileCreateRequest;
import com.city.profile.dto.ProfileDetailResponse;
import com.city.profile.dto.ProfilePublicResponse;
import com.city.profile.dto.ProfileUpdateRequest;

@Service
public class ProfileService {

    private final ProfileRepository  profileRepository;
    private final UserRepository     userRepository;
    private final FileStorageService fileStorageService;
    private final CategoryRepository categoryRepository;
    private final DistrictRepository districtRepository;

    public ProfileService(
            ProfileRepository  profileRepository,
            UserRepository     userRepository,
            FileStorageService fileStorageService,
            CategoryRepository categoryRepository,
            DistrictRepository districtRepository
    ) {
        this.profileRepository  = profileRepository;
        this.userRepository     = userRepository;
        this.fileStorageService = fileStorageService;
        this.categoryRepository = categoryRepository;
        this.districtRepository = districtRepository;
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private String toSlug(String text) {
        if (text == null) return null;
        return text.toLowerCase()
                .replaceAll("[áàäâã]", "a")
                .replaceAll("[éèëê]",  "e")
                .replaceAll("[íìïî]",  "i")
                .replaceAll("[óòöôõ]", "o")
                .replaceAll("[úùüû]",  "u")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }

    private void applyFields(Profile profile,
                              String businessName,
                              String description,
                              String phone,
                              String address,
                              String categorySlug,
                              String districtSlug) {
        if (businessName != null && !businessName.isBlank()) {
            profile.setBusinessName(businessName);
        }
        profile.setDescription(description);
        profile.setPhone(phone);
        profile.setAddress(address);

        if (categorySlug != null && !categorySlug.isBlank()) {
            categoryRepository.findBySlug(toSlug(categorySlug))
                    .ifPresent(profile::setCategory);
        }
        if (districtSlug != null && !districtSlug.isBlank()) {
            districtRepository.findBySlug(toSlug(districtSlug))
                    .ifPresent(profile::setDistrict);
        }
    }

    // ── public API ───────────────────────────────────────────────────────────

    @Transactional
    public Profile createProfile(String email, ProfileCreateRequest req) {
        User user = userRepository.findByEmail(email).orElseThrow();

        if (profileRepository.findByUser_Id(user.getId()).isPresent()) {
            throw new RuntimeException("Ya tienes un perfil");
        }

        Profile profile = new Profile();
        profile.setSlug(SlugUtil.toSlug(req.getBusinessName()));
        profile.setUser(user);
        profile.setStatus(ProfileStatus.PENDING);

        applyFields(profile,
                req.getBusinessName(),
                req.getDescription(),
                req.getPhone(),
                req.getAddress(),
                req.getCategorySlug(),
                req.getDistrictSlug());

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile updateProfile(String email, ProfileUpdateRequest req) {
        Profile profile = profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        applyFields(profile,
                req.getBusinessName(),
                req.getDescription(),
                req.getPhone(),
                req.getAddress(),
                req.getCategorySlug(),
                req.getDistrictSlug());

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile adminUpdateProfile(@NonNull Long id, ProfileUpdateRequest req) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        applyFields(profile,
                req.getBusinessName(),
                req.getDescription(),
                req.getPhone(),
                req.getAddress(),
                req.getCategorySlug(),
                req.getDistrictSlug());

        return profileRepository.save(profile);
    }

    @Transactional
    public Profile uploadLogo(@NonNull Long id, @NonNull String email,
                              @NonNull MultipartFile file) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        if (!profile.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No eres dueño de este perfil");
        }

        String url = fileStorageService.saveProfileLogo(id, file);
        profile.setLogoUrl(url);
        return profileRepository.save(profile);
    }

    @Transactional
    public Profile deleteProfile(@NonNull Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        profileRepository.delete(profile);
        return profile;
    }

    @Transactional(readOnly = true)
    public Profile getBySlug(String slug) {
        return profileRepository.findBySlugWithRelations(slug)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    @Transactional(readOnly = true)
    public Profile getMyProfile(String email) {
        return profileRepository.findByUser_EmailWithRelations(email)
                .orElseThrow(() -> new RuntimeException("El usuario no tiene perfil"));
    }

    @Transactional(readOnly = true)
    public Profile getByEmail(String email) {
        return profileRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    @Transactional
    public Profile approveProfile(Long id) {
        Profile p = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Perfil no encontrado"));
        p.setStatus(ProfileStatus.ACTIVE);
        return profileRepository.save(p);
    }

    @Transactional
    public Profile suspendProfile(Long id) {
        Profile p = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Perfil no encontrado"));
        p.setStatus(ProfileStatus.INACTIVE);
        return profileRepository.save(p);
    }

    public List<Profile> getPendingProfiles() {
        return profileRepository.findByStatus(ProfileStatus.PENDING);
    }

    @Transactional(readOnly = true)
    public ProfileDetailResponse getPublicProfileBySlug(String slug) {
        Profile profile = profileRepository
                .findBySlugAndStatus(slug, ProfileStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return ProfileDetailResponse.from(profile);
    }

    public List<ProfilePublicResponse> getAllActiveProfiles() {
        return profileRepository.findByStatus(ProfileStatus.ACTIVE)
                .stream().map(ProfilePublicResponse::from).toList();
    }

    public List<ProfilePublicResponse> searchPublicProfiles(String q, String category) {
        return profileRepository
                .searchPublicProfiles(ProfileStatus.ACTIVE, "Lurin", q, category)
                .stream().map(ProfilePublicResponse::from).toList();
    }

    public List<ProfilePublicResponse> getPublicProfilesByDistrict(String district) {
        return profileRepository
                .findByStatusAndDistrict_Name(ProfileStatus.ACTIVE, district)
                .stream().map(ProfilePublicResponse::from).toList();
    }

    public List<ProfilePublicResponse> getPublicProfiles(
            String district, String category, String q) {

        boolean hasDistrict = district != null && !district.isBlank();
        List<Profile> profiles;

        if (category != null && q != null) {
            profiles = hasDistrict
                    ? profileRepository.search(ProfileStatus.ACTIVE, district, q)
                            .stream()
                            .filter(p -> p.getCategory() != null &&
                                    p.getCategory().getName().equalsIgnoreCase(category))
                            .toList()
                    : profileRepository.searchPublic(q, category, null,
                            org.springframework.data.domain.Pageable.unpaged()).getContent();

        } else if (category != null) {
            profiles = hasDistrict
                    ? profileRepository.findByStatusAndDistrict_NameAndCategory_Name(
                            ProfileStatus.ACTIVE, district, category)
                    : profileRepository.findByStatus(ProfileStatus.ACTIVE).stream()
                            .filter(p -> p.getCategory() != null &&
                                    p.getCategory().getName().equalsIgnoreCase(category))
                            .toList();

        } else if (q != null) {
            profiles = hasDistrict
                    ? profileRepository.search(ProfileStatus.ACTIVE, district, q)
                    : profileRepository.searchPublic(q, null, null,
                            org.springframework.data.domain.Pageable.unpaged()).getContent();

        } else {
            profiles = hasDistrict
                    ? profileRepository.findByStatusAndDistrict_Name(ProfileStatus.ACTIVE, district)
                    : profileRepository.findByStatus(ProfileStatus.ACTIVE);
        }

        return profiles.stream().map(ProfilePublicResponse::from).toList();
    }
}