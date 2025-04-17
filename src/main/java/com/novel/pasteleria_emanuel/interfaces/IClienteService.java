package com.novel.pasteleria_emanuel.interfaces;

import com.novel.pasteleria_emanuel.models.entities.Cliente;

import java.util.List;


public interface IClienteService {
    // Obtener todos los clientes
    List<Cliente> findAll();

    // Obtener un cliente por su ID
    Cliente findById(Integer id);

    // Guardar o actualizar un cliente
    Cliente save(Cliente cliente);

    // Eliminar un cliente por su ID
    void delete(Integer id);

    // Buscar un cliente por su nombre
    Cliente findByNombre(String nombre);

    Cliente findByDui(String apellido);
}

