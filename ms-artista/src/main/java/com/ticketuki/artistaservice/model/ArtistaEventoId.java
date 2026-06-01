package com.ticketuki.artistaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistaEventoId implements Serializable {
    private Long artistaIdArtista;
    private Long eventoIdEvento;
}