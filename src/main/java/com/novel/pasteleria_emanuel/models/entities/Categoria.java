package com.novel.pasteleria_emanuel.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//Contructor con todos los atributos
@AllArgsConstructor
//contructor sin atributos
@NoArgsConstructor
//Getter y Setter de la etidad
@Data
//Dando estereotipo de Entidad
@Entity
//Definiendo la tabla, con su nombre n catalogo y esquema
@Table(name = "categorias",schema = "public",catalog = "railway")
public class Categoria implements Serializable {
    //definiendo atributos de la entidad
    private static final  long serialVersionUID =1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre",nullable = false,length = 80)
    private String nombre;
}