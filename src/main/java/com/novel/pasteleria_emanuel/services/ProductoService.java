package com.novel.pasteleria_emanuel.services;

import com.novel.pasteleria_emanuel.interfaces.IProductoService;
import com.novel.pasteleria_emanuel.models.entities.Producto;
import com.novel.pasteleria_emanuel.models.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    //inyectando repositorio de producto
    @Autowired
    private ProductoRepository productoRepository;

    //metodo de servicio para obtener productos activos
    @Override
    public List<Producto> findAllActivos() {
        return productoRepository.findAllActivos();
    }

    //metodo de servicio para obtener productos inactivos
    @Override
    public List<Producto> findAllInactivos() {
        return productoRepository.findAllInactivos();
    }

    //metodo de servicio para obtener un producto por el Id
    @Override
    public Producto findById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    //metodo de servicio para guardar un producto
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    //metodo de servicio para cambiar el estado del producto
    @Override
    public Producto changeState(Producto producto) {
        return productoRepository.save(producto);
    }

    //metodo de servicio para verificar si existe un producto
    @Override
    public List<Producto> isExist(Producto producto) {
        return productoRepository.findByNombreAndDescripcion(producto.getNombre(), producto.getDescripcion());
    }
}
