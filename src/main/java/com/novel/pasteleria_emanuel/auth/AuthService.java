package com.novel.pasteleria_emanuel.auth;

import com.novel.pasteleria_emanuel.jwt.JwtService;
import com.novel.pasteleria_emanuel.models.entities.Role;
import com.novel.pasteleria_emanuel.models.entities.Usuario;
import com.novel.pasteleria_emanuel.models.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;



    //inyectamos el servicio de JwtService
    @Autowired
    private JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    //creamos objetos para la autentificacion de usuarios
    private final AuthenticationManager authManager;

    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .nombre(request.getNombre())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(request.getEnabled())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}

