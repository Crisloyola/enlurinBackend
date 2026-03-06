package com.city.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.city.role.Role;
import com.city.role.RoleRepository;
import com.city.user.User;
import com.city.user.UserRepository;

@Configuration
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository  = roleRepository;
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            // 1. Crear roles
            createIfNotExists("USER");
            createIfNotExists("ADMIN");
            createIfNotExists("OWNER");

            // 2. Crear admin por defecto (solo si no existe)
            createAdminIfNotExists(
                "Admin",
                "admin@enlurin.com",   // ← cambia este email
                "admin123"             // ← cambia esta contraseña
            );
        };
    }

    private void createIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(null, roleName));
        }
    }

    private void createAdminIfNotExists(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) return; // ya existe, no hacer nada

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

        User admin = new User(
                name,
                email,
                passwordEncoder.encode(password),
                adminRole
        );

        userRepository.save(admin);
        System.out.println("✅ Admin creado: " + email);
    }
}