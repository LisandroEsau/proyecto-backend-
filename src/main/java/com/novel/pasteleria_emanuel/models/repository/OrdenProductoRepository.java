package com.novel.pasteleria_emanuel.models.repository;


import com.novel.pasteleria_emanuel.models.entities.OrdenProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenProductoRepository extends JpaRepository<OrdenProducto,Integer> {
}
