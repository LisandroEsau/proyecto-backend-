package com.novel.pasteleria_emanuel.controller;


import com.novel.pasteleria_emanuel.interfaces.IProductoService;
import com.novel.pasteleria_emanuel.interfaces.IUploadFileService;
import com.novel.pasteleria_emanuel.models.entities.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductoController {
    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUploadFileService uploadFileService;

    private Logger logger = LoggerFactory.getLogger(ProductoController.class);

    //metodo que trae todas los productos Disponibles
    @GetMapping("/productos")
    public List<Producto> getAllActivos(){
        return productoService.findAllActivos();
    }
    //metodo que trae todas los productos Vendidos
    @GetMapping("/productos/vendidos")
    public List<Producto> getAllInactivos(){
        return productoService.findAllInactivos();
    }
    //metodo que trae el producto por id
    @GetMapping("/productos/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Producto producto = null;
        Map<String,Object> response = new HashMap<>();
        try {
            producto = productoService.findById(id);
        }catch (DataAccessException e){
            response.put("message","Error al realizar la consulta a la clase de datos");
            response.put("error",e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (producto == null) {
            response.put("message","El producto con ID: "+id.toString().concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    //metodo para agregar un nuevo producto
    /*
    * Ejemplo de json para insertar:
    *
    {
        "nombre":"Refrigeradora",
        "descripcion":"Una de las menos vendidas",
        "precio":22,
        "estado":"D",
        "stock": 10,
        "modelo":"p2",
        "marca":{
            "id":"1"
        },
        "categorias":{
            "id":"1"
        }
    }
    * */
    @PostMapping("/productos")
    public ResponseEntity<?> save(@RequestPart Producto producto, @RequestPart(name = "imagen", required = false) MultipartFile imagen) throws IOException {
        //variable para asignar nuevo nombre de la imagen
        String imageNewName = null;
        Map<String,Object> response = new HashMap<>();
        try {
            //evaluamos si el curso no existe en la base de datos
            if (productoService.isExist(producto).size() > 0 && producto.getId() == null) {
                response.put("message","Ya existe un producto con este nombre y descripcion");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CONFLICT);
            }else{
                //persistimos en la base de datos
                if (imagen != null) imageNewName = uploadFileService.copyFile(imagen);
                producto.setImagen(imageNewName);
                productoService.save(producto);
            }
        }catch (DataAccessException e){
            response.put("message","Error al realizar la consulta a la clase de datos");
            response.put("error",e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","Producto Registrado Correctamente...!");
        response.put("producto",producto);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
    //Metodo para Actualizar el estado de el producto
    //http://localhost:8080/api/productos/change-state?estado=D  (Para Cambiar a D = Disponible )
    //http://localhost:8080/api/productos/change-state?estado=V  (Para Cambiar a V = Vendido)
    //Al ingresar un nuevo producto este se registra automaticamente en D
    //Nota adicional: se puede utilizar el json de actualizacion comu solo debe agregar el id
    @PutMapping("/productos/change-state")
    public ResponseEntity<?> changeState(@RequestBody Producto producto,@RequestParam(name = "estado")String estado){
        Map<String,Object> response = new HashMap<>();
        try {
            producto.setEstado(estado);
            productoService.save(producto);
        }catch (DataAccessException e){
            response.put("message","Error al realizar la consulta a la clase de datos");
            response.put("error",e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String strEstado = null;
        if (estado.equals("V")) {
            strEstado = "Vendido";
        }else {
            strEstado = "Disponible";
        }
        response.put("message","El estado del Producto ha cambiado a: ".concat(strEstado));
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
    //metodo para actualizar
    /*
    Ejemplo de el json para actualizar:
    {
        "nombre":"Xd",
        "descripcion":"Una de las menos vendidas",
        "precio":22,
        "estado":"D",
        "stock": 10,
        "modelo":"p2",
        "marca":{
            "id":"1"
        },
        "categorias":{
            "id":"1"
        }
    }
    */
    @PutMapping("/productos/{id}")
    public ResponseEntity<?> update(@RequestPart Producto producto, @RequestPart(name = "imagen", required = false) MultipartFile imagen, @PathVariable Integer id) throws IOException {
        //variable para asignar nuevo nombre de la imagen
        String imageNewName = null;
        //obtener el producto actual
        Producto productoActual = productoService.findById(id);
        Producto productoUpdated = null;
        Map<String,Object> response = new HashMap<>();
        if (productoActual == null) {
            response.put("message","No existe el Producto con ID: " + id.toString().concat(" en la base de datos"));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            //evaluamos si el producto no existe en la base de datos
            if (productoService.isExist(producto).size() > 0 && !productoActual.getId().equals(id)) {
                response.put("message","Ya existe un producto con este nombre y descripcion");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CONFLICT);
            }else{
                //llenamos el productoActual con los nuevos datos
                productoActual.setNombre(producto.getNombre());
                productoActual.setDescripcion(producto.getDescripcion());
                productoActual.setPrecio(producto.getPrecio());
                productoActual.setMarca(producto.getMarca());
                productoActual.setCategoria(producto.getCategoria());
                //persistimos en la base de datos
                if (imagen != null){
                    //verificamos si el producto tiene una image asociada
                    if (productoActual.getImagen() != null && productoActual.getImagen().length() > 0) {
                        String imagenAnterior = productoActual.getImagen();
                        //obtenemos la ruta
                        Path rutaImgAnterior = uploadFileService.getPath(imagenAnterior);
                        //convertimmos a archivo
                        File archivoImgAnterior = rutaImgAnterior.toFile();
                        if (archivoImgAnterior.exists() && archivoImgAnterior.canRead()) {
                            archivoImgAnterior.delete();
                        }
                    }
                    //subimos el nuevo archivo al servidor
                    imageNewName = uploadFileService.copyFile(imagen);
                    productoActual.setImagen(imageNewName);
                }
                //guardamos en la base de datos
                //nota adicional: el framework es capar de identificar si debe gradar o actualizar
                productoUpdated = productoService.save(productoActual);
            }
        }catch (DataAccessException e){
            response.put("message","Error al realizar la consulta a la clase de datos");
            response.put("error",e.getMessage());
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","Producto Actualizado Correctamente...!");
        response.put("producto",productoUpdated);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.ACCEPTED);
    }
//    @PutMapping("/productos/update-stock/{id}")
//    public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable Integer id) {
//
//        Producto productoActual = productoService.findById(id);
//        Producto productoUpdated = null;
//        Map<String,Object> response = new HashMap<>();
//        try {
//            if (productoActual == null) {
//                response.put("message","No existe el Producto con ID: " + id.toString().concat(" en la base de datos"));
//                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
//            }
//            else{
//                Integer restarStock = productoActual.getStock();
//                restarStock--;
//                productoActual.setStock(restarStock);
//                productoUpdated = productoService.save(productoActual);
//            }
//        }catch (DataAccessException e){
//            response.put("message","Error al decrementar stock");
//            response.put("error",e.getMessage());
//            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        response.put("message","Stock decrementado...!");
//        response.put("producto",productoUpdated);
//        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.ACCEPTED);
//    }


}
