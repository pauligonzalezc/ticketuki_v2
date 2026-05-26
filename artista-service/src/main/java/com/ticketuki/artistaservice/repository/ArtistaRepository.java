package com.ticketuki.artistaservice.repository;

import com.ticketuki.artistaservice.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    @Query("SELECT a FROM Artista a WHERE a.genero_artista = :genero")
    List<Artista> findByGenero_artista(@Param("genero") String genero);

    @Query("SELECT a FROM Artista a WHERE LOWER(a.nombre_artista) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Artista> findByNombreContaining(@Param("nombre") String nombre);
}
