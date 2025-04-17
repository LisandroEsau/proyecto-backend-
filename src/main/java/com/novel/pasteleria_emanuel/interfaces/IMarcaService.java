package com.novel.pasteleria_emanuel.interfaces;

import com.novel.pasteleria_emanuel.models.entities.Marca;

import java.util.List;

public interface IMarcaService {
    //Metodo para obtener todas las Marcas
    public List<Marca> findAll();
    //Metodo para obtener Marcas por Id
    public Marca findById(Integer id);
    //Metodo para Guardar y actualizar Marca
    public Marca save(Marca marca);
    //Metodo para borrar Marca
    public void delete(Integer id);
    //Metodo para traer nombre de la marca
    public Marca findByNombre(String nombre);
}
