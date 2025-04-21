package com.novel.pasteleria_emanuel.config;


import com.novel.pasteleria_emanuel.models.entities.Role;
import com.novel.pasteleria_emanuel.models.entities.Usuario;
import com.novel.pasteleria_emanuel.models.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DefaultAdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            String adminUsername = "admin";

            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                Usuario admin = Usuario.builder()
                        .nombre("Administrador")
                        .username(adminUsername)
                        .email("admin@admin.com")
                        .password(passwordEncoder.encode("admin123"))
                        .enabled(true)
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);
                System.out.println("Usuario ADMIN creado por defecto");
            } else {
                System.out.println("Usuario ADMIN ya existe");
            }
        };
    }
}
