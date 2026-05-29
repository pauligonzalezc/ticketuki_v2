package com.ticketuki.promocionservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionRequestDTO {

    @NotBlank(message = "La empresa es requerida")
    private String empresa;

    @NotNull(message = "El descuento es requerido")
    @Min(value = 1, message = "El descuento debe ser al menos 1%")
    @Max(value = 100, message = "El descuento no puede superar 100%")
    private Integer descuento;

    @NotBlank(message = "La descripción es requerida")
    private String descripcion;

    @NotBlank(message = "La restricción es requerida")
    private String restriccion;

    @NotNull(message = "La fecha de expiración es requerida")
    private LocalDate fecha_expiracion;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate fecha_inicio;
}
