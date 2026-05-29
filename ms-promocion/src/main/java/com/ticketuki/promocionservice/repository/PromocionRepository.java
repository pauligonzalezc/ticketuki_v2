package com.ticketuki.promocionservice.repository;

import com.ticketuki.promocionservice.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {

    List<Promocion> findByEmpresaIgnoreCase(String empresa);

    @Query("SELECT p FROM Promocion p WHERE p.fecha_inicio <= :hoy AND p.fecha_expiracion >= :hoy")
    List<Promocion> findPromocionesActivas(@Param("hoy") LocalDate hoy);

}
