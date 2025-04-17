package com.novel.pasteleria_emanuel.services;

import com.novel.pasteleria_emanuel.interfaces.IOrdenService;
import com.novel.pasteleria_emanuel.models.entities.Orden;
import com.novel.pasteleria_emanuel.models.entities.OrdenProducto;
import com.novel.pasteleria_emanuel.models.repository.OrdenProductoRepository;
import com.novel.pasteleria_emanuel.models.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrdenService implements IOrdenService {

    //inyectando repositorias
    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    //metodo de servicio para traer todas las ordenes con estado activo
    @Override
    //marcando como metodo transaccional solo de Recibido
    @Transactional(readOnly = true)
    public List<Orden> findAll(Date fechaInicio) {
        Date fechaFinal = null;
        if (fechaInicio != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(fechaInicio);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            fechaFinal = c.getTime();
            return ordenRepository.findAllActivasWithRangoFechas(fechaInicio,fechaFinal);
        }
        return ordenRepository.findAllActivas();
    }

    //metodo de servicio para traer todas las ordenes canceladas
    @Override
    @Transactional
    public List<Orden> findAllCanceladas(Date fechaInicio) {
        Date fechaFinal = null;
        if (fechaInicio != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(fechaInicio);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            fechaFinal = c.getTime();
            return ordenRepository.findAllCanceladasWithRangoFechas(fechaInicio,fechaFinal);
        }
        return ordenRepository.findAllCanceladas();
    }

    //metodo de servicio para guardar y actualizar
    @Override
    @Transactional
    public Orden seveOrUpdate(Orden orden) {
        Orden ordenPersisted = null;
        try{
            if (orden.getId() == null) {//hara un insert
                //obtenemos las ordes en una lista para la facturacion
                List<OrdenProducto> productos= orden.getOrdenProductoList();
                orden.setOrdenProductoList(new ArrayList<OrdenProducto>());
                //persistimos el objeto
                ordenPersisted = ordenRepository.save(orden);
                //recorremos la coleccion de productos para guardar
                for (OrdenProducto producto:productos) {
                    producto.setOrden(ordenPersisted);
                }
                ordenProductoRepository.saveAll(productos);
            }else {
                //se hara un update
                for (OrdenProducto producto:orden.getOrdenProductoList()) {
                    producto.setOrden(orden);
                }
                ordenPersisted = ordenRepository.save(orden);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ordenPersisted;

    }

    //metodo de servicio para cambiar estado de una orden
    @Override
    public Orden changeState(Orden orden) {
        return ordenRepository.save(orden);
    }

    //metodo de servicio para traer orden por id
    @Override
    public Orden findById(Integer id) {
        return ordenRepository.findById(id).orElse(null);
    }
}
