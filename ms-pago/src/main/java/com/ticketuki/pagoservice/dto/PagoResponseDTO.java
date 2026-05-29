package com.ticketuki.pagoservice.dto;

import com.ticketuki.pagoservice.model.EstadoPago;
import com.ticketuki.pagoservice.model.MedioPago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {

    private Long id_pago;
    private Long monto;
    private MedioPago medio_pago;
    private String cod_autorizacion;
    private LocalDateTime timestamp;
    private EstadoPago estado;
    private Long venta_id;
    private Long usuario_id;
}
