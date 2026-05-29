package com.ticketuki.ventaservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PromocionDTO {
    private Long id_promocion;
    private Integer descuento;
    private LocalDate fecha_inicio;
    private LocalDate fecha_expiracion;
}
