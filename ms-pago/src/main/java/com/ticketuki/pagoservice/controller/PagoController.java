package com.ticketuki.pagoservice.controller;

import com.ticketuki.pagoservice.dto.PagoRequestDTO;
import com.ticketuki.pagoservice.dto.PagoResponseDTO;
import com.ticketuki.pagoservice.model.EstadoPago;
import com.ticketuki.pagoservice.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Slf4j
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDTO> procesarPago(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.procesarPago(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPago(id));
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<PagoResponseDTO>> listarPorVenta(@PathVariable Long idVenta) {
        return ResponseEntity.ok(pagoService.listarPorVenta(idVenta));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PagoResponseDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pagoService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoResponseDTO>> listarPorEstado(@PathVariable EstadoPago estado) {
        return ResponseEntity.ok(pagoService.listarPorEstado(estado));
    }

    @GetMapping("/fecha/{inicio}/{fin}")
    public ResponseEntity<List<PagoResponseDTO>> listarPorPeriodo(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(pagoService.listarPorPeriodo(inicio, fin));
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<PagoResponseDTO> completarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.completarPago(id));
    }

    @PutMapping("/{id}/reembolso")
    public ResponseEntity<PagoResponseDTO> procesarReembolso(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.procesarReembolso(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PagoResponseDTO> cancelarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.cancelarPago(id));
    }
}
