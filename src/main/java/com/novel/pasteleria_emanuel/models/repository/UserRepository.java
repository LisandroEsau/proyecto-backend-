package com.novel.pasteleria_emanuel.models.repository;


import com.novel.pasteleria_emanuel.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);
}