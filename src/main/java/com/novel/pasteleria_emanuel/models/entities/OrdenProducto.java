package com.novel.pasteleria_emanuel.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "ordenes_productos", schema = "public", catalog = "railway")
public class OrdenProducto implements Serializable {
    //definiendo atributos de la entidad
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    //Definiendo llaves foraneas
    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id",nullable = false)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "inventario_id", referencedColumnName = "id",nullable = false)
    private Inventario inventario;
    @ManyToOne
    @JsonBackReference//para evitar que se produsca una relacion infinita
    @JoinColumn(name = "orden_id", referencedColumnName = "id",nullable = false)
    private Orden orden;
}
