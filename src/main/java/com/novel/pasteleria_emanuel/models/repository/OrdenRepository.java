package com.novel.pasteleria_emanuel.models.repository;

import com.novel.pasteleria_emanuel.models.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrdenRepository extends JpaRepository <Orden,Integer> {
    //Query de JPQL para obtener las ordenes con estado Activo en orden de fecha descendente
    @Query("FROM Orden i WHERE i.estado ='R' ORDER BY i.fecha DESC")
    List<Orden> findAllActivas();

    //Query de JPQL para obtener las ordenes con estado Canceladas en orden de fecha descendente
    @Query("FROM Orden i WHERE i.estado ='C' ORDER BY i.fecha DESC")
    List<Orden> findAllCanceladas();

    //Query de JPQL para obtener las ordenes con estado Activo segun su fecha de Inicio y su fecha final
    @Query("FROM Orden  i WHERE i.estado ='R' AND i.fecha BETWEEN :fechaInicio AND :fechaFinal ORDER BY i.fecha DESC ")
    List<Orden> findAllActivasWithRangoFechas(@Param("fechaInicio")Date fechaInicio, @Param("fechaFinal") Date fechaFinal);

    //Query de JPQL para obtener las ordenes con estado Canceladas segun su fecha de Inicio y su fecha final
    @Query("FROM Orden  i WHERE i.estado ='C' AND i.fecha BETWEEN :fechaInicio AND :fechaFinal ORDER BY i.fecha DESC ")
    List<Orden> findAllCanceladasWithRangoFechas(@Param("fechaInicio")Date fechaInicio, @Param("fechaFinal") Date fechaFinal);
}
