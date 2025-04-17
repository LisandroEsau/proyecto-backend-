package com.novel.pasteleria_emanuel.models.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.novel.pasteleria_emanuel.config.CustomAuthorityDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

//Contructor con todos los atributos


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuarios", schema = "public",catalog = "pasteleria_emanuel")
public class Usuario implements Serializable, UserDetails {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name ="nombre",nullable = false,length = 80)
    private  String nombre;
    @Column(name ="username",nullable = false,length = 20)
    private  String username;
    @Column(name ="email",nullable = false,length = 80)
    private  String email;
    @Column(name ="password",nullable = false,length = 150)
    private  String password;
    @Column(name = "enable",nullable = false)
    private  Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
        //return UserDetails.super.isEnabled();
    }
}

