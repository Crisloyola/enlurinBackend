package com.city.profile;

import java.util.List;

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
import com.city.profile.dto.ProfileDetailResponse;
import com.city.profile.dto.ProfilePublicResponse;
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

        // ADMIN: aprobar perfil
        public Profile approveProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profile.setStatus(ProfileStatus.ACTIVE);
        return profileRepository.save(profile);
        }

        // ADMIN: suspender perfil
        public Profile suspendProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        profile.setStatus(ProfileStatus.PENDING);
        return profileRepository.save(profile);
        }

        // ADMIN: listar pendientes
        public List<Profile> getPendingProfiles() {
        return profileRepository.findByStatus(ProfileStatus.PENDING);
        }

        public List<ProfilePublicResponse> getPublicProfilesByDistrict(String district) {
                return profileRepository
                        .findByStatusAndDistrict_Name(ProfileStatus.ACTIVE, district)
                        .stream()
                        .map(ProfilePublicResponse::from)
                        .toList();
        }

        public List<ProfilePublicResponse> searchPublicProfiles(
                String q,
                String category
        ) {
        return profileRepository
                .searchPublicProfiles(
                        ProfileStatus.ACTIVE,
                        "Lurin",
                        q,
                        category
                )
                .stream()
                .map(ProfilePublicResponse::from)
                .toList();
        }

        // Público: ver perfil por slug
        public ProfileDetailResponse getPublicProfileBySlug(String slug) {
               Profile profile = profileRepository
            .findBySlugAndStatus(slug, ProfileStatus.ACTIVE)
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
                return ProfileDetailResponse.from(profile);
        }

        public List<ProfilePublicResponse> getPublicProfiles(
                String district,
                String category,
                String q
        ) {

        List<Profile> profiles;

        if (category != null && q != null) {
                profiles = profileRepository.search(
                        ProfileStatus.ACTIVE,
                        district,
                        q
                ).stream()
                .filter(p -> p.getCategory().getName().equalsIgnoreCase(category))
                .toList();

        } else if (category != null) {
                profiles = profileRepository
                        .findByStatusAndDistrict_NameAndCategory_Name(
                                ProfileStatus.ACTIVE,
                                district,
                                category
                        );

        } else if (q != null) {
                profiles = profileRepository.search(
                        ProfileStatus.ACTIVE,
                        district,
                        q
                );

        } else {
                profiles = profileRepository
                        .findByStatusAndDistrict_Name(
                                ProfileStatus.ACTIVE,
                                district
                        );
        }

        return profiles.stream()
                .map(ProfilePublicResponse::from)
                .toList();
        }
}