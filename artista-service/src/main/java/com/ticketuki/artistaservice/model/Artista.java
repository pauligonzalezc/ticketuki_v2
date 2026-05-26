package com.ticketuki.artistaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private Long id_artista;

    @NotBlank(message = "El nombre del artista es requerido")
    @Column(name = "nombre_artista", nullable = false, length = 50)
    private String nombre_artista;

    @NotBlank(message = "El género del artista es requerido")
    @Column(name = "genero_artista", nullable = false, length = 50)
    private String genero_artista;

    @NotBlank(message = "Las redes sociales son requeridas")
    @Column(name = "redes_sociales", nullable = false, length = 25)
    private String redes_sociales;
}
