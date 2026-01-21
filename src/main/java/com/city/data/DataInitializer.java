package com.city.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.city.role.Role;
import com.city.role.RoleRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Component("dataInitializerMain")
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {

            createIfNotExists("ROLE_USER");
            createIfNotExists("ROLE_ADMIN");
            createIfNotExists("ROLE_OWNER");
        };
    }

    private void createIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(null, roleName));
    }
}
}
