package com.ticketuki.recintoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direccion_recinto")
public class DireccionRecinto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_direccion_recinto")
    private Long id_direccion_recinto;

    @NotBlank(message = "La calle es requerida")
    @Column(name = "calle", nullable = false, length = 50)
    private String calle;

    @NotBlank(message = "La region es requerida")
    @Column(name = "region", nullable = false, length = 50)
    private String region;

    @NotBlank(message = "La comuna es requerida")
    @Column(name = "comuna", nullable = false, length = 50)
    private String comuna;

    @NotNull(message = "El numero de calle es requerido")
    @Column(name = "num_calle", nullable = false)
    private Integer num_calle;

    @Column(name = "referencia_acceso", length = 100)
    private String referencia_acceso;
}
