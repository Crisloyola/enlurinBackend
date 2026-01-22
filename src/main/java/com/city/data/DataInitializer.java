package com.city.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.city.role.Role;
import com.city.role.RoleRepository;

@Configuration
public class DataInitializer {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            createIfNotExists("USER");
            createIfNotExists("ADMIN");
            createIfNotExists("OWNER");
        };
    }

    private void createIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(null, roleName));
        }
    }
}
