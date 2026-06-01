package com.ticketuki.artistaservice.service;

import com.ticketuki.artistaservice.dto.ArtistaRequestDTO;
import com.ticketuki.artistaservice.dto.ArtistaResponseDTO;
import com.ticketuki.artistaservice.model.Artista;
import com.ticketuki.artistaservice.model.ArtistaEventoId;
import com.ticketuki.artistaservice.model.Artista_Evento;
import com.ticketuki.artistaservice.exception.ArtistaEventoDuplicadoException;
import com.ticketuki.artistaservice.exception.ArtistaNotFoundException;
import com.ticketuki.artistaservice.repository.ArtistaEventoRepository;
import com.ticketuki.artistaservice.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final ArtistaEventoRepository artistaEventoRepository;

    // ✅ Getters generados por Lombok con camelCase: getIdArtista(), getNombreArtista()...
    private ArtistaResponseDTO toResponseDTO(Artista artista) {
        return new ArtistaResponseDTO(
                artista.getIdArtista(),
                artista.getNombreArtista(),
                artista.getGeneroArtista(),
                artista.getRedesSociales()
        );
    }

    @Transactional
    public ArtistaResponseDTO crearArtista(ArtistaRequestDTO dto) {
        log.info("Creando artista: {}", dto.getNombre_artista());
        Artista artista = new Artista(null, dto.getNombre_artista(), dto.getGenero_artista(), dto.getRedes_sociales());
        return toResponseDTO(artistaRepository.save(artista));
    }

    @Transactional
    public ArtistaResponseDTO actualizarArtista(Long id, ArtistaRequestDTO dto) {
        log.info("Actualizando artista con ID: {}", id);
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Artista no encontrado para actualizar: {}", id);
                    return new ArtistaNotFoundException("Artista no encontrado: " + id);
                });
        artista.setNombreArtista(dto.getNombre_artista());
        artista.setGeneroArtista(dto.getGenero_artista());
        artista.setRedesSociales(dto.getRedes_sociales());
        return toResponseDTO(artistaRepository.save(artista));
    }

    @Transactional(readOnly = true)
    public ArtistaResponseDTO obtenerArtista(Long id) {
        return toResponseDTO(artistaRepository.findById(id)
                .orElseThrow(() -> new ArtistaNotFoundException("Artista no encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<ArtistaResponseDTO> listarArtistas() {
        log.info("Listando todos los artistas");
        return artistaRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public void eliminarArtista(Long id) {
        if (!artistaRepository.existsById(id)) {
            throw new ArtistaNotFoundException("Artista no encontrado: " + id);
        }
        artistaEventoRepository.deleteAll(artistaEventoRepository.findByArtistaIdArtista(id));
        artistaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ArtistaResponseDTO> listarPorGenero(String genero) {
        log.info("Listando artistas por género: {}", genero);
        return artistaRepository.findByGeneroArtista(genero).stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public void asociarArtistaEvento(Long idArtista, Long idEvento) {
        if (!artistaRepository.existsById(idArtista)) {
            throw new ArtistaNotFoundException("Artista no encontrado: " + idArtista);
        }
        if (artistaEventoRepository.existsByArtistaIdArtistaAndEventoIdEvento(idArtista, idEvento)) {
            throw new ArtistaEventoDuplicadoException("El artista " + idArtista + " ya está asociado al evento " + idEvento);
        }
        artistaEventoRepository.save(new Artista_Evento(idArtista, idEvento));
    }

    @Transactional
    public void desasociarArtistaEvento(Long idArtista, Long idEvento) {
        if (!artistaEventoRepository.existsByArtistaIdArtistaAndEventoIdEvento(idArtista, idEvento)) {
            throw new ArtistaNotFoundException("Asociación no encontrada para artista " + idArtista + " y evento " + idEvento);
        }
        artistaEventoRepository.deleteById(new ArtistaEventoId(idArtista, idEvento));
    }

    @Transactional(readOnly = true)
    public List<ArtistaResponseDTO> obtenerArtistasPorEvento(Long idEvento) {
        return artistaEventoRepository.findArtistasByEventoId(idEvento)
                .stream().map(this::toResponseDTO).toList();
    }
}