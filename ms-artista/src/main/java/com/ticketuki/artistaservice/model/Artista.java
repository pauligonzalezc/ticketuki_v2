package com.ticketuki.artistaservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artista")
    private Long idArtista;

    @Column(name = "nombre_artista", nullable = false, length = 50)
    private String nombreArtista;

    @Column(name = "genero_artista", nullable = false, length = 50)
    private String generoArtista;

    @Column(name = "redes_sociales", nullable = false, length = 255)
    private String redesSociales;
}
