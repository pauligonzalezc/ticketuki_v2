package com.ticketuki.artistaservice.repository;

import com.ticketuki.artistaservice.model.Artista_Evento;
import com.ticketuki.artistaservice.model.ArtistaEventoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistaEventoRepository extends JpaRepository<Artista_Evento, ArtistaEventoId> {

    @Query("SELECT ae FROM Artista_Evento ae WHERE ae.evento_id_evento = :idEvento")
    List<Artista_Evento> findByEvento_id_evento(@Param("idEvento") Long idEvento);

    @Query("SELECT ae FROM Artista_Evento ae WHERE ae.artista_id_artista = :idArtista")
    List<Artista_Evento> findByArtista_id_artista(@Param("idArtista") Long idArtista);

    @Query("SELECT CASE WHEN COUNT(ae) > 0 THEN true ELSE false END FROM Artista_Evento ae WHERE ae.artista_id_artista = :idArtista AND ae.evento_id_evento = :idEvento")
    boolean existsByArtista_id_artistaAndEvento_id_evento(@Param("idArtista") Long idArtista, @Param("idEvento") Long idEvento);
}
