package com.novel.pasteleria_emanuel.models.repository;

import com.novel.pasteleria_emanuel.models.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    // Productos ya vencidos
    List<Inventario> findByFechaVencimientoBefore(LocalDate fecha);

    // Productos por vencer (en los próximos X días)
    List<Inventario> findByFechaVencimientoBetween(LocalDate desde, LocalDate hasta);

    //Buscar por lote
    List<Inventario> findByLoteContainingIgnoreCase(String lote);

    // Buscar por producto (por ID del producto)
    List<Inventario> findByProductoId(Integer productoId);

}