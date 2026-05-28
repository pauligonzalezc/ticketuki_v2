package com.ticketuki.recintoservice.controller;

import com.ticketuki.recintoservice.dto.RecintoDTO;
import com.ticketuki.recintoservice.service.RecintoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/recintos")
@RequiredArgsConstructor
@Slf4j
public class RecintoController {

    private final RecintoService recintoService;

    @GetMapping
    public ResponseEntity<List<RecintoDTO>> listarRecintos() {
        log.info("GET /recintos - Listando recintos");
        return ResponseEntity.ok(recintoService.listarRecintos());
    }

    @PostMapping
    public ResponseEntity<RecintoDTO> crearRecinto(@Valid @RequestBody RecintoDTO dto) {
        log.info("POST /recintos - Creando recinto");
        return ResponseEntity.status(HttpStatus.CREATED).body(recintoService.crearRecinto(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecintoDTO> obtenerRecinto(@PathVariable Long id) {
        log.info("GET /recintos/{} - Obteniendo recinto", id);
        return ResponseEntity.ok(recintoService.obtenerRecinto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecintoDTO> actualizarRecinto(@PathVariable Long id, @Valid @RequestBody RecintoDTO dto) {
        log.info("PUT /recintos/{} - Actualizando recinto", id);
        return ResponseEntity.ok(recintoService.actualizarRecinto(id, dto));
    }
}
