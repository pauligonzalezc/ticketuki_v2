package com.ticketuki.artistaservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artista_evento")
@IdClass(ArtistaEventoId.class)
public class Artista_Evento {

    @Id
    @Column(name = "artista_id_artista")
    private Long artista_id_artista;

    @Id
    @Column(name = "evento_id_evento")
    private Long evento_id_evento;
}
