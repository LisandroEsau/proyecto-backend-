package com.novel.pasteleria_emanuel.models.repository;


import com.novel.pasteleria_emanuel.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    //Derived Query para obtener nombre y descripcion de  Producto
    List<Producto> findByNombreAndDescripcion(String nombre, String descripcion);
    //Query de JPQL para obtener  lso productos con estado activo
    @Query("FROM Producto p WHERE p.estado = 'D' ORDER BY p.id DESC")
    List<Producto> findAllActivos();
    //Query de JPQL para obtener  lso productos con estado inactivo
    @Query("FROM Producto p WHERE p.estado = 'V' ORDER BY p.id DESC")
    List<Producto> findAllInactivos();
}