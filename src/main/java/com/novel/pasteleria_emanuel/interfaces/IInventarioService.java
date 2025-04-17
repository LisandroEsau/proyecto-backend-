package com.novel.pasteleria_emanuel.interfaces;

import com.novel.pasteleria_emanuel.models.entities.Categoria;
import com.novel.pasteleria_emanuel.models.entities.Inventario;

import java.util.List;

public interface IInventarioService {

    // Obtener todo el inventario
    List<Inventario> obtenerTodos();

    //  Obtener productos vencidos
    List<Inventario> obtenerVencidos();

    // ️ Obtener productos que vencerán en X días
    List<Inventario> obtenerPorVencerEnXDias(int dias);

    //  Buscar por lote
    List<Inventario> buscarPorLote(String lote);

    //  Buscar por producto (por ID)
    List<Inventario> buscarPorProductoId(Integer productoId);

    public Inventario save(Inventario inventario);
    //Metodo para borrar Categoria
    public void delete(Integer id);
}