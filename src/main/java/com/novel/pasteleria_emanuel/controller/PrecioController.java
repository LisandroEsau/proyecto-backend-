package com.novel.pasteleria_emanuel.controller;


import com.novel.pasteleria_emanuel.interfaces.IPrecioService;
import com.novel.pasteleria_emanuel.models.entities.Precio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PrecioController {

    @Autowired
    private IPrecioService precioService;

    // Obtener todos los precios
    @GetMapping("/precios")
    public ResponseEntity<List<Precio>> getAll() {
        List<Precio> precios = precioService.findAll();
        return ResponseEntity.ok(precios);
    }

    // Obtener un precio por ID
    @GetMapping("/precios/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        Precio precio;
        try {
            precio = precioService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la búsqueda por ID");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (precio == null) {
            response.put("message", "El precio con ID: " + id + " no existe en la base de datos.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(precio, HttpStatus.OK);
    }

    // Insertar nuevo precio
    @PostMapping("/precios")
    public ResponseEntity<?> save(@RequestBody Precio precio) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validación simple de nombre repetido (opcional)
            Precio precioExistente = precioService.findByNombre(precio.getNombre());
            if (precioExistente != null && precio.getId() == null) {
                response.put("message", "Ya existe un precio con este nombre. Ingrese otro.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            precioService.save(precio);
        } catch (DataAccessException e) {
            response.put("message", "Error al insertar el registro.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Precio registrado con éxito!");
        response.put("precio", precio);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualizar precio
    @PutMapping("/precios/{id}")
    public ResponseEntity<?> update(@RequestBody Precio datos, @PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        Precio precioActual = precioService.findById(id);

        if (precioActual == null) {
            response.put("message", "No se puede editar el Precio con ID: " + id + ", no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Precio precioExistente = precioService.findByNombre(datos.getNombre());
            if (precioExistente != null && !precioExistente.getId().equals(id)) {
                response.put("message", "Ya existe un precio con ese nombre. Ingrese otro.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            precioActual.setNombre(datos.getNombre());
            precioActual.setValor(datos.getValor());

            Precio actualizado = precioService.save(precioActual);

            response.put("message", "¡Precio actualizado con éxito!");
            response.put("precio", actualizado);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar el registro.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar precio
    @DeleteMapping("/precios/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        Precio precio = precioService.findById(id);

        if (precio == null) {
            response.put("message", "No se puede eliminar el Precio con ID: " + id + ", no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            precioService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar el registro.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "¡Precio eliminado exitosamente!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
