package com.novel.pasteleria_emanuel.services;


import com.novel.pasteleria_emanuel.interfaces.ICategoriaService;
import com.novel.pasteleria_emanuel.models.entities.Categoria;
import com.novel.pasteleria_emanuel.models.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {

    //iyectando dependencia del repositorio de categoria
    @Autowired
    private CategoriaRepository ctgRepository;

    //metodo de servicio para obtener todas las Categorias
    @Override
    public List<Categoria> findAll() {
        return ctgRepository.findAll();
    }

    //metodo de servicio para obtener todas las Categorias por Id
    @Override
    public Categoria findById(Integer id) {
        return ctgRepository.findById(id).orElse(null);
    }

    //metodo de servicio para guardar una categoria
    @Override
    public Categoria save(Categoria categoria) {
        return ctgRepository.save(categoria);
    }

    //metodo de servicio para borrar una categoria
    @Override
    public void delete(Integer id) {
        ctgRepository.deleteById(id);
    }

    //metodo de servicio para obtener el nombre de la categoria
    @Override
    public Categoria findByNombre(String nombre) {
        return ctgRepository.findByNombre(nombre);
    }
}
