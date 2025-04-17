package com.novel.pasteleria_emanuel.interfaces;

import com.novel.pasteleria_emanuel.models.entities.Categoria;

import java.util.List;

public interface ICategoriaService {
    //Metodo para obtener todos las Categorias
    public List<Categoria> findAll();
    //Metodo para traer Categoria por Id
    public Categoria findById(Integer id);
    //Metodo para guardar y actualizar Categorias
    public Categoria save(Categoria categoria);
    //Metodo para borrar Categoria
    public void delete(Integer id);
    //metodo para obtener nombre de la Categoria
    public Categoria findByNombre(String nombre);
}