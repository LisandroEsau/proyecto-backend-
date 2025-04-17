package com.novel.pasteleria_emanuel.interfaces;

import com.novel.pasteleria_emanuel.models.entities.Precio;

import java.util.List;

public interface IPrecioService {

    // Obtener todos los precios
    List<Precio> findAll();

    // Buscar precio por ID
    Precio findById(Integer id);

    // Guardar o actualizar un precio
    Precio save(Precio precio);

    // Eliminar precio por ID
    public void delete(Integer id);

    // Buscar por nombre
    Precio findByNombre(String nombre);

    // Buscar por valor
    Precio findByValor(double valor);

    // Buscar por nombre parecido (LIKE)
    List<Precio> findByNombreContaining(String nombre);

    // Buscar por valor mayor que
    List<Precio> findByValorGreaterThan(double valor);

    // Buscar por valor menor que
    List<Precio> findByValorLessThan(double valor);

    // Buscar por valor en un rango
    List<Precio> findByValorBetween(double min, double max);
}