package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.dto.MedicionRequestDTO;
import edu.ucaldas.clima.dto.MedicionResponseDTO;
import edu.ucaldas.clima.dto.PromedioTemperaturaResponseDTO;
import edu.ucaldas.clima.service.MedicionService;
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
@RequestMapping("/api/mediciones")
public class MedicionController {

    private final MedicionService medicionService;

    public MedicionController(MedicionService medicionService) {
        this.medicionService = medicionService;
    }

    @PostMapping
    public ResponseEntity<MedicionResponseDTO> crear(
            @Valid @RequestBody MedicionRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicionService.crear(request));
    }

    @GetMapping("/estacion/{id}")
    public ResponseEntity<List<MedicionResponseDTO>> listarPorEstacion(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(medicionService.listarPorEstacion(id));
    }

    @GetMapping("/promedio/ciudad/{ciudadId}")
    public ResponseEntity<PromedioTemperaturaResponseDTO> obtenerPromedioPorCiudad(
            @PathVariable Long ciudadId
    ) {
        return ResponseEntity.ok(
                medicionService.obtenerPromedioTemperaturaPorCiudad(ciudadId)
        );
    }
}
