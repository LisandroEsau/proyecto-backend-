package com.novel.pasteleria_emanuel.models.repository;

import com.novel.pasteleria_emanuel.models.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
    //Derived Query para obener las Categorias
    Categoria findByNombre(String nombre);
}
