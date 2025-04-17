package com.novel.pasteleria_emanuel.models.repository;

import com.novel.pasteleria_emanuel.models.entities.Precio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecioRepository extends JpaRepository<Precio, Integer> {

    // Buscar por nombre exacto
    Precio findByNombre(String nombre);

    // Buscar por valor exacto
    Precio findByValor(double valor);

    // Buscar precios por nombre que contenga algo (LIKE %nombre%)
    List<Precio> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por valor mayor que
    List<Precio> findByValorGreaterThan(double valor);

    // Buscar por valor menor que
    List<Precio> findByValorLessThan(double valor);

    // Buscar por valor entre un rango
    List<Precio> findByValorBetween(double min, double max);
}
