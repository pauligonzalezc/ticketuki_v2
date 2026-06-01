package com.ticketuki.ticketservice.repository;

import com.ticketuki.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByCodQr(String codQr);

    List<Ticket> findByVentaIdVenta(Long ventaIdVenta);

    List<Ticket> findByEventoIdEvento(Long eventoIdEvento);

    List<Ticket> findBySectorIdSector(Long sectorIdSector);

    List<Ticket> findByRunTitular(String runTitular);

    boolean existsByNumAsientoAndEventoIdEventoAndSectorIdSector(
            Integer numAsiento, Long eventoIdEvento, Long sectorIdSector);
}