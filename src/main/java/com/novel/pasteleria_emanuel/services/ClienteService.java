package com.novel.pasteleria_emanuel.services;

import com.novel.pasteleria_emanuel.interfaces.IClienteService;
import com.novel.pasteleria_emanuel.models.entities.Cliente;
import com.novel.pasteleria_emanuel.models.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {
    //iyectando dependencia del repositorio de cliente
    @Autowired
    private ClienteRepository ClRepository;

    //metodo de servicio para obtener todos clientes
    @Override
    public List<Cliente> findAll() {
        return ClRepository.findAll();
    }

    //metodo de servicio para obtener todas los clientes por Id
    @Override
    public Cliente findById(Integer id) {
        return ClRepository.findById(id).orElse(null);
    }

    //metodo de servicio para guardar un cliente
    @Override
    public Cliente save(Cliente cliente) {
        return ClRepository.save(cliente);
    }

    //metodo de servicio para borrar un cliente
    @Override
    public void delete(Integer id) {
        ClRepository.deleteById(id);
    }

    @Override
    public Cliente findByDui(String dui) {
        return ClRepository.findByDui(dui);
    }

    //metodo de servicio para obtener el nombre de un cliente
    @Override
    public Cliente findByNombre(String nombre) {
        return ClRepository.findByNombre(nombre);
    }
}
