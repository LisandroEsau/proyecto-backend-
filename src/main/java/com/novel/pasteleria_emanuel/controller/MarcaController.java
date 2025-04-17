package com.novel.pasteleria_emanuel.controller;

import com.novel.pasteleria_emanuel.interfaces.IMarcaService;
import com.novel.pasteleria_emanuel.models.entities.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MarcaController {
    @Autowired
    private IMarcaService marcaService;

    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> getAll(){
        List<Marca> marcas = marcaService.findAll();
        return ResponseEntity.ok(marcas);
    }

    //metodo para obtener una marca por id
    @GetMapping("/marcas/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Marca marca = null;
        Map<String, Object> response = new HashMap<>();
        try {
            marca = marcaService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la búsqueda por id");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (marca == null) {
            response.put("message", "La marca con ID: " + id + " no existe en la base de datos."); // Agregar espacio
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    //metodo para insertr una marca
    @PostMapping("/marcas")
    public ResponseEntity<?>save(@RequestBody Marca marca){
        Map<String, Object> response = new HashMap<>();
        try{
            //verificaamos si existe el nombre de la marca de la base de datos
            Marca mrcExist = marcaService.findByNombre(marca.getNombre());
            if(mrcExist != null && marca.getId()==null){
                response.put("message","ya existe una marca con este nombre digite otra");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
            }else{
                //hacemos persisrencia la entidad
                marcaService.save(marca);
            }
        }catch (DataAccessException e){
            response.put("message","Error al insertar el registro intente de nuevo");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","Marca registrada con exito...!");
        response.put("marca",marca);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    //metodo para actulizar nombre
    @PutMapping("/marcas/{id}")
    public ResponseEntity<?> update(@RequestBody Marca esp, @PathVariable Integer id) {
        Marca mrcActual = marcaService.findById(id);
        Map<String, Object> response = new HashMap<>();

        // Si no existe el ID de la marca, enviamos un mensaje
        if (mrcActual == null) {
            response.put("message", "No se puede editar la Marca con ID: " +
                    id.toString() + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Verificamos si el nuevo nombre de la especialidad no existe en la base de datos
            Marca mrcExist = marcaService.findByNombre(esp.getNombre());
            if (mrcExist != null && !mrcExist.getId().equals(id)) {
                response.put("message", "Ya existe una marca con este nombre, digite otro.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } else {
                // Aquí puedes verificar que esp.getNombre() no sea nulo antes de asignar
                if (esp.getNombre() == null) {
                    response.put("message", "El campo nombre no puede ser nulo.");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                mrcActual.setNombre(esp.getNombre());
                Marca mrcActualizado = marcaService.save(mrcActual);
                response.put("message", "Marca actualizada con éxito..!");
                response.put("marca", mrcActualizado);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            response.put("message", "Error al editar el registro, intente de nuevo.");
            response.put("Error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //metodo para eliminar Marca
    @DeleteMapping("/marcas/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        //obteniendo la marca actual
        Marca mrcActual = marcaService.findById(id);
        Map<String, Object> response = new HashMap<>();
        if(mrcActual == null){
            response.put("message","No se puede eliminar la Marca con el ID: "+
                    id.toString().concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            marcaService.delete(id);
        }catch (DataAccessException e){
            response.put("message","Error al eliminar el registro intete de nuevo");
            response.put("error",e.getMessage());
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","Marca eliminada...!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
