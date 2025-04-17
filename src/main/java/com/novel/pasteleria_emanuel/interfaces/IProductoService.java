package com.novel.pasteleria_emanuel.interfaces;


import com.novel.pasteleria_emanuel.models.entities.Producto;

import java.util.List;

public interface IProductoService {
    //Metodo para traer todos los  Productos con estado activo
    public List<Producto> findAllActivos();
    //Metodo para traer todos los Productos con estado inactivo
    public List<Producto> findAllInactivos();
    //Metodo para traer todos los Productos por su Id
    public Producto findById(Integer id);
    //Metodo para Guardar Producto
    public Producto save(Producto producto);
    //Metodo para cambiar estado del Producto
    public Producto changeState(Producto producto);
    //Metodo para verifivar si exite el Producto
    public  List<Producto> isExist (Producto producto);
}