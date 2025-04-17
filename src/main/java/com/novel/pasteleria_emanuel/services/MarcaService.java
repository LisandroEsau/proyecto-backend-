package com.novel.pasteleria_emanuel.services;

import com.novel.pasteleria_emanuel.interfaces.IMarcaService;
import com.novel.pasteleria_emanuel.models.entities.Marca;
import com.novel.pasteleria_emanuel.models.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService implements IMarcaService {
    //inyectando el repositorio
    @Autowired
    private MarcaRepository mrcRepository;

    //metodo de servicio para par obtener todas las Marcas
    @Override
    public List<Marca> findAll() {
        return mrcRepository.findAll();
    }

    //metodo de servicio para obtener Marcas por su Id
    @Override
    public Marca findById(Integer id) {
        return mrcRepository.findById(id).orElse(null);
    }

    //metodo de servicio para guardar una marca
    @Override
    public Marca save(Marca marca) {
        return mrcRepository.save(marca);
    }

    //metodo de servicio para borrar marcas
    @Override
    public void delete(Integer id) {
        mrcRepository.deleteById(id);
    }

    //metodo de servicio para el nombre de una marca
    @Override
    public Marca findByNombre(String nombre) {
        return mrcRepository.findByNombre(nombre);
    }
}
