package com.ticketuki.pagoservice.service;

import com.ticketuki.pagoservice.dto.PagoRequestDTO;
import com.ticketuki.pagoservice.dto.PagoResponseDTO;
import com.ticketuki.pagoservice.dto.TicketResumenDTO;
import com.ticketuki.pagoservice.exception.PagoNotFoundException;
import com.ticketuki.pagoservice.model.EstadoPago;
import com.ticketuki.pagoservice.model.Pago;
import com.ticketuki.pagoservice.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient ventaWebClient;
    private final WebClient usuarioWebClient;
    private final WebClient ticketWebClient;

    @Value("${ms.estado.ticket.cancelado.id}")
    private Long idEstadoTicketCancelado;

    @Value("${ms.estado.venta.anulada.id}")
    private Long idEstadoVentaAnulada;

    public PagoService(PagoRepository pagoRepository,
                       @Qualifier("ventaWebClient") WebClient ventaWebClient,
                       @Qualifier("usuarioWebClient") WebClient usuarioWebClient,
                       @Qualifier("ticketWebClient") WebClient ticketWebClient) {
        this.pagoRepository = pagoRepository;
        this.ventaWebClient = ventaWebClient;
        this.usuarioWebClient = usuarioWebClient;
        this.ticketWebClient = ticketWebClient;
    }

    private PagoResponseDTO toResponseDTO(Pago p) {
        return new PagoResponseDTO(p.getId_pago(), p.getMonto(), p.getMedio_pago(),
                p.getCod_autorizacion(), p.getTimestamp(), p.getEstado(),
                p.getVenta_id(), p.getUsuario_id());
    }

    private void validarVentaExiste(Long ventaId) {
        try {
            ventaWebClient.get()
                    .uri("/ventas/{id}", ventaId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> Mono.error(new IllegalArgumentException("Venta no encontrada con id: " + ventaId)))
                    .bodyToMono(Void.class)
                    .block();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.warn("No se pudo validar venta {}: {}", ventaId, e.getMessage());
        }
    }

    private void validarUsuarioExiste(Long usuarioId) {
        try {
            usuarioWebClient.get()
                    .uri("/usuarios/{id}", usuarioId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> Mono.error(new IllegalArgumentException("Usuario no encontrado con id: " + usuarioId)))
                    .bodyToMono(Void.class)
                    .block();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.warn("No se pudo validar usuario {}: {}", usuarioId, e.getMessage());
        }
    }

    private void cancelarTicketsDeLaVenta(Long ventaId) {
        try {
            List<TicketResumenDTO> tickets = ticketWebClient.get()
                    .uri("/tickets/venta/{ventaId}", ventaId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<TicketResumenDTO>>() {})
                    .block();

            if (tickets == null || tickets.isEmpty()) {
                log.warn("No se encontraron tickets para la venta {}", ventaId);
                return;
            }

            for (TicketResumenDTO ticket : tickets) {
                try {
                    ticketWebClient.put()
                            .uri("/tickets/{id}/estado/{idEstado}", ticket.getId_ticket(), idEstadoTicketCancelado)
                            .retrieve()
                            .bodyToMono(Void.class)
                            .block();
                    log.info("Ticket {} cancelado", ticket.getId_ticket());
                } catch (Exception e) {
                    log.warn("No se pudo cancelar ticket {}: {}", ticket.getId_ticket(), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.warn("No se pudo obtener tickets de la venta {}: {}", ventaId, e.getMessage());
        }
    }

    private void anularVenta(Long ventaId) {
        try {
            ventaWebClient.put()
                    .uri("/ventas/{id}/estado/{idEstado}", ventaId, idEstadoVentaAnulada)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            log.info("Venta {} anulada", ventaId);
        } catch (Exception e) {
            log.warn("No se pudo anular la venta {}: {}", ventaId, e.getMessage());
        }
    }

    @Transactional
    public PagoResponseDTO procesarPago(PagoRequestDTO dto) {
        log.info("Procesando pago para venta: {}", dto.getVenta_id());
        validarVentaExiste(dto.getVenta_id());
        validarUsuarioExiste(dto.getUsuario_id());
        Pago p = new Pago(null, dto.getMonto(), dto.getMedio_pago(), dto.getCod_autorizacion(),
                LocalDateTime.now(), EstadoPago.PENDIENTE, dto.getVenta_id(), dto.getUsuario_id());
        return toResponseDTO(pagoRepository.save(p));
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPago(Long id) {
        return pagoRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorVenta(Long idVenta) {
        return pagoRepository.findByVenta_id(idVenta).stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public PagoResponseDTO completarPago(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
        if (p.getEstado() != EstadoPago.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se puede completar un pago en estado PENDIENTE. Estado actual: " + p.getEstado());
        }
        p.setEstado(EstadoPago.COMPLETADO);
        return toResponseDTO(pagoRepository.save(p));
    }

    @Transactional
    public PagoResponseDTO procesarReembolso(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
        if (p.getEstado() != EstadoPago.COMPLETADO) {
            throw new IllegalStateException(
                    "Solo se puede reembolsar un pago en estado COMPLETADO. Estado actual: " + p.getEstado());
        }
        p.setEstado(EstadoPago.REEMBOLSADO);
        PagoResponseDTO resultado = toResponseDTO(pagoRepository.save(p));

        log.info("Reembolso aprobado para pago {}. Coordinando cancelación de tickets y venta {}",
                id, p.getVenta_id());
        cancelarTicketsDeLaVenta(p.getVenta_id());
        anularVenta(p.getVenta_id());

        return resultado;
    }

    @Transactional
    public PagoResponseDTO cancelarPago(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
        if (p.getEstado() != EstadoPago.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se puede cancelar un pago en estado PENDIENTE. Estado actual: " + p.getEstado());
        }
        p.setEstado(EstadoPago.CANCELADO);
        return toResponseDTO(pagoRepository.save(p));
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorUsuario(Long idUsuario) {
        return pagoRepository.findByUsuario_id(idUsuario).stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorEstado(EstadoPago estado) {
        return pagoRepository.findByEstado(estado).stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPorPeriodo(LocalDate inicio, LocalDate fin) {
        return pagoRepository.findByTimestampBetween(
                inicio.atStartOfDay(), fin.atTime(23, 59, 59)
        ).stream().map(this::toResponseDTO).toList();
    }
}
