package com.ticketuki.recintoservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionRecintoDTO {
    private Long id_direccion_recinto;

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "La región es obligatoria")
    private String region;

    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;

    @NotNull(message = "El número de calle es obligatorio")
    private Integer num_calle;

    private String referencia_acceso;
}
