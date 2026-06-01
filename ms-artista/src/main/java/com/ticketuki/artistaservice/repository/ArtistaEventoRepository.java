package com.ticketuki.artistaservice.repository;

import com.ticketuki.artistaservice.model.Artista;
import com.ticketuki.artistaservice.model.Artista_Evento;
import com.ticketuki.artistaservice.model.ArtistaEventoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistaEventoRepository extends JpaRepository<Artista_Evento, ArtistaEventoId> {

    List<Artista_Evento> findByEventoIdEvento(Long eventoIdEvento);

    List<Artista_Evento> findByArtistaIdArtista(Long artistaIdArtista);

    boolean existsByArtistaIdArtistaAndEventoIdEvento(Long artistaIdArtista, Long eventoIdEvento);

    // ✅ JPQL ahora coincide con los campos camelCase de las entidades
    @Query("SELECT a FROM Artista a WHERE a.idArtista IN " +
            "(SELECT ae.artistaIdArtista FROM Artista_Evento ae WHERE ae.eventoIdEvento = :idEvento)")
    List<Artista> findArtistasByEventoId(@Param("idEvento") Long idEvento);
}