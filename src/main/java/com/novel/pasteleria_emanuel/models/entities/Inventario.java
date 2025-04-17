package com.novel.pasteleria_emanuel.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

//Contructor con todos los atributos
@AllArgsConstructor
//contructor sin atributos
@NoArgsConstructor
//Getter y Setter de la etidad
@Data
//Dando estereotipo de Entidad
@Entity
@Table(name = "inventario",schema = "public",catalog = "railway")
public class Inventario implements Serializable{
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(name = "lote",nullable = false,length = 100)
    private String lote;
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "stock",nullable = false,columnDefinition = "Decimal(10,2)")
    private Integer stock;

    //Definiendo llaves foraneas
    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id",nullable = false)
    private Producto producto;

//    @Transient
//    public boolean isVencido() {
//        return fechaVencimiento.before(new Date());
//    }


}
