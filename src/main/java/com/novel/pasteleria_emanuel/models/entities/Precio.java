package com.novel.pasteleria_emanuel.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// Constructor con todos los atributos
@AllArgsConstructor
// Constructor sin atributos
@NoArgsConstructor
// Getter y Setter de la entidad
@Data
// Dando estereotipo de Entidad
@Entity
// Definiendo la tabla con su nombre, catálogo y esquema
@Table(name = "precios", schema = "public", catalog = "pasteleria_emanuel")
public class Precio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre",nullable = false,length = 80)
    private String nombre;

    @Column(name = "valor", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private double valor;

}
