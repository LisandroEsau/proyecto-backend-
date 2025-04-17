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
@Table(name = "detalles_ordenes",schema = "public",catalog = "pasteleria_emanuel")
public class DetalleOrden implements Serializable {
    //definiendo atributos de la entidad
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(name = "cantidad",nullable = false,columnDefinition = "Decimal(8,2)")
    private String cantidad;
    @Column(name = "precio",nullable = false,columnDefinition = "Decimal(10,2)")
    private String precio;

    //Definiendo llaves foraneas
    @ManyToOne
    @JoinColumn(name = "productos_id", referencedColumnName = "id",nullable = false )
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "inventario_id", referencedColumnName = "id",nullable = false)
    private Inventario inventario;
    @ManyToOne
    @JoinColumn(name = "ordenes_id", referencedColumnName = "id",nullable = false )
    private Orden orden;

}
