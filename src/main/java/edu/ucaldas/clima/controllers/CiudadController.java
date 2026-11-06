package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.dto.CiudadRequestDTO;
import edu.ucaldas.clima.dto.CiudadResponseDTO;
import edu.ucaldas.clima.service.CiudadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    private final CiudadService ciudadService;

    public CiudadController(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    @GetMapping
    public ResponseEntity<List<CiudadResponseDTO>> listar() {
        return ResponseEntity.ok(ciudadService.listar());
    }

    @PostMapping
    public ResponseEntity<CiudadResponseDTO> crear(
            @Valid @RequestBody CiudadRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadService.crear(request));
    }
}
