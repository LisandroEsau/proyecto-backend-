package com.novel.pasteleria_emanuel.controller;

import com.novel.pasteleria_emanuel.interfaces.IInventarioService;
import com.novel.pasteleria_emanuel.models.entities.Inventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class InventarioController {

    @Autowired
    private IInventarioService inventarioService;

    // Obtener todo el inventario
    @GetMapping("/inventario")
    public ResponseEntity<List<Inventario>> getAll() {
        List<Inventario> inventarios = inventarioService.obtenerTodos();
        return ResponseEntity.ok(inventarios);
    }

    // Obtener productos vencidos
    @GetMapping("/inventario/vencidos")
    public ResponseEntity<List<Inventario>> getVencidos() {
        List<Inventario> vencidos = inventarioService.obtenerVencidos();
        return ResponseEntity.ok(vencidos);
    }

    // Obtener productos que vencerán en X días
    @GetMapping("/inventario/por-vencer")
    public ResponseEntity<List<Inventario>> getPorVencerEnXDias(@RequestParam int dias) {
        List<Inventario> porVencer = inventarioService.obtenerPorVencerEnXDias(dias);
        return ResponseEntity.ok(porVencer);
    }

    // Buscar por lote
    @GetMapping("/inventario/lote/{lote}")
    public ResponseEntity<?> buscarPorLote(@PathVariable String lote) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Inventario> resultado = inventarioService.buscarPorLote(lote);
            if (resultado.isEmpty()) {
                response.put("message", "No se encontraron productos con el lote: " + lote);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al buscar por lote.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar por ID del producto
    @GetMapping("/inventario/producto/{id}")
    public ResponseEntity<?> buscarPorProductoId(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Inventario> resultado = inventarioService.buscarPorProductoId(id);
            if (resultado.isEmpty()) {
                response.put("message", "No se encontraron registros de inventario para el producto con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("message", "Error al buscar por producto.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/inventario")
    public ResponseEntity<?> saveInventario(@RequestBody Inventario inventario) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validación de fecha de vencimiento
            Date hoy = new Date();
            if (inventario.getFechaVencimiento() != null && inventario.getFechaVencimiento().equals(hoy)) {
                response.put("message", "No se puede registrar un producto con fecha de vencimiento pasada.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Buscar inventario existente por producto, lote y fecha
            List<Inventario> inventariosExistentes = inventarioService.buscarPorProductoId(inventario.getProducto().getId());

            Optional<Inventario> inventarioExistente = inventariosExistentes.stream()
                    .filter(i -> i.getLote().equalsIgnoreCase(inventario.getLote()) &&
                            i.getFechaVencimiento().equals(inventario.getFechaVencimiento()))
                    .findFirst();

            if (inventarioExistente.isPresent()) {
                Inventario existente = inventarioExistente.get();
                existente.setStock(existente.getStock() + inventario.getStock());
                Inventario actualizado = inventarioService.save(existente);

                response.put("message", "Stock actualizado para lote y fecha existentes.");
                response.put("inventario", actualizado);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            // Guardar nuevo inventario
            Inventario nuevoInventario = inventarioService.save(inventario);
            response.put("message", "Nuevo inventario guardado exitosamente.");
            response.put("inventario", nuevoInventario);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("message", "Error al guardar el inventario.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Método para guardar un nuevo inventario
//    @PostMapping("/inventario")
//    public ResponseEntity<?> saveInventario(@RequestBody Inventario inventario) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            Inventario nuevoInventario = inventarioService.save(inventario);
//            response.put("message", "Inventario guardado exitosamente.");
//            response.put("inventario", nuevoInventario);
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (DataAccessException e) {
//            response.put("message", "Error al guardar el inventario.");
//            response.put("error", e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}

