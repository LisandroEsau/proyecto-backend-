package com.novel.pasteleria_emanuel.services;


import com.novel.pasteleria_emanuel.interfaces.IPrecioService;
import com.novel.pasteleria_emanuel.models.entities.Precio;
import com.novel.pasteleria_emanuel.models.repository.PrecioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrecioService implements IPrecioService {

    @Autowired
    private PrecioRepository precioRepository;

    @Override
    public List<Precio> findAll() {
        return precioRepository.findAll();
    }

    @Override
    public Precio findById(Integer id) {
        return precioRepository.findById(id).orElse(null);
    }

    @Override
    public Precio save(Precio precio) {
        return precioRepository.save(precio);
    }

    @Override
    public void delete(Integer id) {
        precioRepository.deleteById(id);
    }

    @Override
    public Precio findByNombre(String nombre) {
        return precioRepository.findByNombre(nombre);
    }

    @Override
    public Precio findByValor(double valor) {
        return precioRepository.findByValor(valor);
    }

    @Override
    public List<Precio> findByNombreContaining(String nombre) {
        return precioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Precio> findByValorGreaterThan(double valor) {
        return precioRepository.findByValorGreaterThan(valor);
    }

    @Override
    public List<Precio> findByValorLessThan(double valor) {
        return precioRepository.findByValorLessThan(valor);
    }

    @Override
    public List<Precio> findByValorBetween(double min, double max) {
        return precioRepository.findByValorBetween(min, max);
    }
}
