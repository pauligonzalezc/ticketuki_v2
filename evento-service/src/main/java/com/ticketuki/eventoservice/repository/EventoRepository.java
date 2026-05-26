package com.ticketuki.eventoservice.repository;

import com.ticketuki.eventoservice.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT e FROM Evento e WHERE e.fecha_evento = :fecha")
    List<Evento> findByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT e FROM Evento e WHERE e.recinto_id_recinto = :idRecinto")
    List<Evento> findByRecinto(@Param("idRecinto") Long idRecinto);

    @Query("SELECT e FROM Evento e WHERE LOWER(e.nombre_evento) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Evento> findByNombreContaining(@Param("nombre") String nombre);
}
