package com.novel.pasteleria_emanuel.controller;

import com.novel.pasteleria_emanuel.interfaces.ICategoriaService;
import com.novel.pasteleria_emanuel.models.entities.Categoria;
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
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;
    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> getAll(){
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    //metodo para obtener una categoria por id
    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Categoria categoria = null;
        Map<String, Object> response = new HashMap<>();
        try {
            categoria = categoriaService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error en la búsqueda por id");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (categoria == null) {
            response.put("message", "La categoria con ID: " + id + " no existe en la base de datos."); // Agregar espacio
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }
    //metodo para insertr una categoria
    @PostMapping("/categorias")
    public ResponseEntity<?>save(@RequestBody Categoria categoria){
        Map<String, Object> response = new HashMap<>();
        try{
            //verificaamos si existe el nombre de la categoria de la base de datos
            Categoria ctExist = categoriaService.findByNombre(categoria.getNombre());
            if(ctExist != null && categoria.getId()==null){
                response.put("message","ya existe una categoria con este nombre digite otra");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CONFLICT);
            }else{
                //hacemos persisrencia la entidad
                categoriaService.save(categoria);
            }
        }catch (DataAccessException e){
            response.put("message","Error al insertar el registro intente de nuevo");
            response.put("Error", e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","Categoria registrada con exito...!");
        response.put("categoria",categoria);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    //metodo para actulizar nombre
    @PutMapping("/categorias/{id}")
    public ResponseEntity<?> update(@RequestBody Categoria esp, @PathVariable Integer id) {
        Categoria ctActual = categoriaService.findById(id);
        Map<String, Object> response = new HashMap<>();

        // Si no existe la categoría con el ID proporcionado
        if (ctActual == null) {
            response.put("message", "No se puede editar la categoría con ID: " +
                    id.toString() + ", no existe en la base de datos.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Verificamos si el nuevo nombre de la categoría ya existe en la base de datos
            Categoria ctExist = categoriaService.findByNombre(esp.getNombre());
            if (ctExist != null && !ctExist.getId().equals(id)) {
                response.put("message", "Ya existe una categoría con este nombre, digite otro.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            // Validamos que el nombre no sea nulo
            if (esp.getNombre() == null || esp.getNombre().isEmpty()) {
                response.put("message", "El campo nombre no puede ser nulo o estar vacío.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Actualizamos el nombre de la categoría
            ctActual.setNombre(esp.getNombre());
            Categoria ctUpdated = categoriaService.save(ctActual);

            response.put("message", "Categoría actualizada con éxito.");
            response.put("categoria", ctUpdated);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response.put("message", "Error al editar el registro, intente de nuevo.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("message", "Error inesperado.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //metodo para eliminar categoria
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        //obteniendo la marca actual
        Categoria ctActual = categoriaService.findById(id);
        Map<String, Object> response = new HashMap<>();
        if(ctActual == null){
            response.put("message","No se puede eliminar la Categoria con el ID: "+
                    id.toString().concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            categoriaService.delete(id);
        }catch (DataAccessException e){
            response.put("message","Error al eliminar el registro intete de nuevo");
            response.put("error",e.getMessage());
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","Categoria eliminada...!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

}
