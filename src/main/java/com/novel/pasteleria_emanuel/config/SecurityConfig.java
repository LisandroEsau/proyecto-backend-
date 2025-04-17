package com.novel.pasteleria_emanuel.config;

import com.novel.pasteleria_emanuel.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita la protección CSRF
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/auth/**").permitAll() // Permite todas las peticiones a /auth/**
                        .requestMatchers(HttpMethod.GET, "/api/productos/**", "/api/marcas").permitAll() // Permite GET en /api/marcas/** y /api/productos
                        .requestMatchers(HttpMethod.POST,"/api/productos/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/precios/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/precios/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/productos/change-state").hasAnyAuthority("ADMIN")
                        //.requestMatchers(HttpMethod.GET,"/api//users").hasAnyAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/images/productos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clientes").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/inventario").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/marcas/**").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST,"/api/marcas/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/marcas/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/categorias/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/categorias/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/ordenes/**").hasAuthority("USER")

                        .anyRequest().hasAnyAuthority("ADMIN")
                ).cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //.formLogin(Customizer.withDefaults()) // Configuración de inicio de sesión predeterminada
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowedOrigins(List.of("*"));
//        config.setAllowedOriginPatterns(List.of("*"));
       config.setAllowedOrigins(List.of("http://localhost:5173/"));
        //config.setAllowedOriginPatterns(Arrays.asList("http://192.168.100.108:3000", "http://*"));
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**" ,config);

        return source;
    }
}
