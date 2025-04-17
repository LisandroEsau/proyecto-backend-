package com.novel.pasteleria_emanuel.models.repository;

import com.novel.pasteleria_emanuel.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    //Derived Query para obener los Clientes
    Cliente findByNombre(String nombre);
    Cliente findByEmail(String email);
    Cliente findByDui(String dui);
    boolean existsByTelefono(String telefono);
}
