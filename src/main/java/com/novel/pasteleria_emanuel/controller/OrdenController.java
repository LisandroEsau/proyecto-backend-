package com.novel.pasteleria_emanuel.controller;

import com.novel.pasteleria_emanuel.models.entities.Orden;
import com.novel.pasteleria_emanuel.services.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins  = "*")
@RequestMapping("/api")
public class OrdenController {
    @Autowired
    private OrdenService ordenService;

    //metodo para obtener todas la ordenes Recibidas
    @GetMapping("/ordenes")
    public List<Orden> getAllActivas(@RequestParam(name = "fecha", required = false) Date fecha){
        return ordenService.findAll(fecha);
    }

    //metodo para obtener ordenes canceladas
    @GetMapping("/ordenes/canceladas")
    public List<Orden> getAllCanceladas(@RequestParam(name = "fecha", required = false) Date fecha){
        return ordenService.findAllCanceladas(fecha);
    }
    //metodo para registrar nueva orden
    @PostMapping("/ordenes")
    public ResponseEntity<?> saveOrUpdate(@RequestBody Orden orden){
        Map<String,Object> response = new HashMap<>();
        Orden ordenPersisted = null;
        try {
            ordenPersisted =ordenService.seveOrUpdate(orden);
        }catch (DataAccessException e){
            response.put("error","Error "+ e.getMessage());
            return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ordenPersisted != null) {
            response.put("message","La orden se realizo correctamente...!");
            response.put("inscripcion",ordenPersisted);
        }else {
            response.put("message","Error al insertar o actualizar la orden, intente de nuevo");
        }
        return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }
    //metodo para obtener una Orden por id
    @GetMapping("/ordenes/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Orden orden = null;
        Map<String,Object> response = new HashMap<>();
        try{
            orden = ordenService.findById(id);
        }catch (DataAccessException e){
            response.put("error",e.getMessage());
            return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (orden == null){
            response.put("message","La orden con ID: " + id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        response.put("orden",orden);
        return  new ResponseEntity<Orden>(orden, HttpStatus.OK);
    }

    //metodo para cambiar estado de orden
    @PutMapping("/ordenes/change-state")
    public ResponseEntity<?> changeState(@RequestBody Orden orden,@RequestParam(name ="estado")String estado){
        Map<String,Object> response = new HashMap<>();
        Orden ordenPersisted = null;
        try {
            orden.setEstado(estado);
            ordenPersisted = ordenService.seveOrUpdate(orden);
        }catch (DataAccessException e){
            response.put("error", e.getMessage());
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String strStado = null;
        if (estado.equals("C")) strStado = "Cancelada";
        else strStado ="Recibido";
        response.put("message","El estado de la orden a cambiado a: "+ strStado);
        response.put("orden",ordenPersisted);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
