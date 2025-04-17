package com.novel.pasteleria_emanuel.models.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
//contructor sin atributos
@NoArgsConstructor
//Getter y Setter de la etidad
@Data
//Dando estereotipo de Entidad
@Entity
//Definiendo la tabla, con su nombre n catalogo y esquema
@Table(name = "clientes",schema = "public",catalog = "pasteleria_emanuel")
public class Cliente implements Serializable {
    private static final  long serialVersionUID =1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre",nullable = false,length = 80)
    private String nombre;
    @Column(name = "email", nullable = false,length = 80)
    private String email;
    @Column (name = "dui", nullable = false, length = 10)
    private String dui;
    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;
    @JsonProperty("n_registro")
    @Column(name = "n_registro", nullable = false, unique = true)
    private Integer nRegistro;



}

