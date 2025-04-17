package com.novel.pasteleria_emanuel.interfaces;


import com.novel.pasteleria_emanuel.models.entities.Orden;

import java.util.Date;
import java.util.List;

public interface IOrdenService {
    //Metodo para traer todas las Ordenes segun la fecha
    public List<Orden> findAll(Date fechaInicio);
    //Metodo para traer todas las Ordenes Canceladad segun la fecha
    public List<Orden> findAllCanceladas(Date fechaInicio);
    //Metodo para guardar y actualizar Orden
    public  Orden seveOrUpdate(Orden orden);
    //Metodo para cambiar el estado de la orden
    public  Orden changeState(Orden orden);
    //Metodo par traer la Orden por Id
    public  Orden findById(Integer id);
}
