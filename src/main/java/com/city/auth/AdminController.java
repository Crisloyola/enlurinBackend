package com.city.auth;

import com.city.profile.ProfileRepository;
import com.city.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public AdminController(UserRepository userRepository,
                           ProfileRepository profileRepository) {
        this.userRepository    = userRepository;
        this.profileRepository = profileRepository;
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // Primero eliminar el perfil asociado (si existe)
        profileRepository.findByUser_Id(id)
            .ifPresent(profileRepository::delete);

        // Luego eliminar el usuario
        userRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}