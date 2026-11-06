package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.dto.EstacionRequestDTO;
import edu.ucaldas.clima.dto.EstacionResponseDTO;
import edu.ucaldas.clima.service.EstacionService;
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
@RequestMapping("/api/estaciones")
public class EstacionController {

    private final EstacionService estacionService;

    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    @PostMapping
    public ResponseEntity<EstacionResponseDTO> crear(
            @Valid @RequestBody EstacionRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estacionService.crear(request));
    }

    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<EstacionResponseDTO>> listarPorCiudad(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(estacionService.listarPorCiudad(id));
    }
}
