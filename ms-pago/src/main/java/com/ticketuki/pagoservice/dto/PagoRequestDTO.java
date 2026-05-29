package com.ticketuki.pagoservice.dto;

import com.ticketuki.pagoservice.model.MedioPago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser mayor a 0")
    private Long monto;

    @NotNull(message = "El medio de pago es requerido")
    private MedioPago medio_pago;

    @NotBlank(message = "El código de autorización es requerido")
    private String cod_autorizacion;

    @NotNull(message = "El ID de venta es requerido")
    private Long venta_id;

    @NotNull(message = "El ID de usuario es requerido")
    private Long usuario_id;
}
