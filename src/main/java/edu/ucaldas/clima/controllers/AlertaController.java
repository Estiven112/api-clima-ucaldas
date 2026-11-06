package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.dto.AlertaRequestDTO;
import edu.ucaldas.clima.dto.AlertaResponseDTO;
import edu.ucaldas.clima.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @PostMapping
    public ResponseEntity<AlertaResponseDTO> crear(
            @Valid @RequestBody AlertaRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaService.crear(request));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<AlertaResponseDTO>> listarPorNivel(
            @PathVariable String nivel
    ) {
        return ResponseEntity.ok(alertaService.listarPorNivel(nivel));
    }
}
