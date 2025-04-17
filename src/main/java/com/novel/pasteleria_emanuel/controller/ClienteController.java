package com.novel.pasteleria_emanuel.controller;


import com.novel.pasteleria_emanuel.interfaces.IClienteService;
import com.novel.pasteleria_emanuel.models.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    // Obtener un cliente por ID
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la búsqueda por ID");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("message", "El cliente con ID: " + id + " no existe en la base de datos.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // Crear nuevo cliente
    @PostMapping("/clientes")
    public ResponseEntity<?> save(@RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();

        try {
            Cliente existingByDui = clienteService.findByDui(cliente.getDui());
            if (existingByDui != null && cliente.getId() == null) {
                response.put("message", "Ya existe un cliente con este DUI.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("message", "Error al insertar el cliente.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Cliente registrado exitosamente.");
        response.put("cliente", cliente);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualizar un cliente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente clienteData, @PathVariable Integer id) {
        Cliente actual = clienteService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (actual == null) {
            response.put("message", "No se puede editar el cliente con ID: " + id + ", no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Cliente exist = clienteService.findByNombre(clienteData.getNombre());
            if (exist != null && !exist.getId().equals(id)) {
                response.put("message", "Ya existe un cliente con este nombre. Ingrese otro.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            // Validaciones de campos (puedes agregar más)
            if (clienteData.getNombre() == null || clienteData.getNombre().isEmpty()) {
                response.put("message", "El campo nombre no puede estar vacío.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Actualizamos los campos
            actual.setNombre(clienteData.getNombre());
            actual.setDui(clienteData.getDui());
            actual.setTelefono(clienteData.getTelefono());
            actual.setEmail(clienteData.getEmail());

            Cliente updated = clienteService.save(actual);
            response.put("message", "Cliente actualizado con éxito.");
            response.put("cliente", updated);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar el cliente.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar cliente
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (cliente == null) {
            response.put("message", "No se puede eliminar el cliente con ID: " + id + ", no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el cliente.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Cliente eliminado con éxito.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
