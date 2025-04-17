package com.novel.pasteleria_emanuel.services;

import com.novel.pasteleria_emanuel.interfaces.IInventarioService;
import com.novel.pasteleria_emanuel.models.entities.Inventario;
import com.novel.pasteleria_emanuel.models.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventarioService implements IInventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    @Override
    public List<Inventario> obtenerVencidos() {
        return inventarioRepository.findByFechaVencimientoBefore(LocalDate.now());
    }

    @Override
    public List<Inventario> obtenerPorVencerEnXDias(int dias) {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(dias);
        return inventarioRepository.findByFechaVencimientoBetween(hoy, limite);
    }

    @Override
    public List<Inventario> buscarPorLote(String lote) {
        return inventarioRepository.findByLoteContainingIgnoreCase(lote);
    }

    @Override
    public List<Inventario> buscarPorProductoId(Integer productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    @Override
    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);  // Guardamos el inventario y retornamos el objeto inventario guardado
    }

    // Método de servicio para borrar un inventario
    @Override
    public void delete(Integer id) {
        inventarioRepository.deleteById(id);  // Eliminamos el inventario por id
    }
}