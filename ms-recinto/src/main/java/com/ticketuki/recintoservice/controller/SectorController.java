package com.ticketuki.recintoservice.controller;

import com.ticketuki.recintoservice.dto.SectorDTO;
import com.ticketuki.recintoservice.service.SectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sectores")
@RequiredArgsConstructor
@Slf4j
public class SectorController {

    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<SectorDTO> crearSector(@Valid @RequestBody SectorDTO dto) {
        log.info("POST /sectores - Creando sector");
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorService.crearSector(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorDTO> obtenerSector(@PathVariable Long id) {
        log.info("GET /sectores/{} - Obteniendo sector", id);
        return ResponseEntity.ok(sectorService.obtenerSector(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorDTO> actualizarSector(@PathVariable Long id, @Valid @RequestBody SectorDTO dto) {
        log.info("PUT /sectores/{} - Actualizando sector", id);
        return ResponseEntity.ok(sectorService.actualizarSector(id, dto));
    }

    @GetMapping("/recinto/{recintoId}")
    public ResponseEntity<List<SectorDTO>> listarPorRecinto(@PathVariable Long recintoId) {
        log.info("GET /sectores/recinto/{} - Listando sectores por recinto", recintoId);
        return ResponseEntity.ok(sectorService.listarPorRecinto(recintoId));
    }
}
