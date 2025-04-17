package com.novel.pasteleria_emanuel.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//Contructor con todos los atributos
@AllArgsConstructor
//contructor sin atributos
@NoArgsConstructor
//Getter y Setter de la etidad
@Data
//Dando estereotipo de Entidad
@Entity
//Definiendo la tabla, con su nombre , catalogo y esquema
@Table(name = "ordenes",schema = "public",catalog = "railway")
public class Orden implements Serializable {
    //definiendo atributos de la entidad
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(name = "correlativo",nullable = false,length = 10)
    private String correlativo;
    @Column(name = "fecha",nullable = false)
    private Date fecha;
    @Column(name = "estado",nullable = false,length = 1)
    private String estado;
    @Column(name = "monto",nullable = false,columnDefinition = "Decimal(10,2)")
    private double monto;

    //Definiendo llaves foraneas
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id",nullable = false )
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id",nullable = false )
    private Cliente cliente;
    @OneToMany(mappedBy = "orden", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrdenProducto> ordenProductoList;

    @PrePersist
    private void setEstado(){
        this.estado = "R";
    }
}
