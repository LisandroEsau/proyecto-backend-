package com.novel.pasteleria_emanuel.models.repository;

import com.novel.pasteleria_emanuel.models.entities.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca,Integer> {
    //se definen los metodos muy paraticular que no estan en jpaRepository
    //Derived Query para obener las Marcas
    Marca findByNombre(String nombre);
}