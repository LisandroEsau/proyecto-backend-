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
@Table(name = "productos",schema = "public",catalog = "railway")
public class Producto implements Serializable {
    //definiendo atributos de la entidad
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(name = "nombre",nullable = false,length = 100)
    private String nombre;
    @Column(name = "descripcion",nullable = false,length = 250)
    private String descripcion;
    @Column(name = "imagen",nullable = true,length = 100)
    private String imagen;
    @Column(name = "estado",nullable = false,length = 1)
    private String estado;

    //Definiendo llaves foraneas
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marcas_id", referencedColumnName = "id",nullable = false )
    private Marca marca;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorias_id", referencedColumnName = "id",nullable = false )
    //en caso de error verificar la categoria(razon: se realizo un refactorizacion del nombre de categorias a categoria en el atributo)
    private Categoria categoria;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "precios_id", referencedColumnName = "id",nullable = false )
    private Precio precio;

    @PrePersist
    private void setEstado(){
        this.estado = "D";
    }
}
