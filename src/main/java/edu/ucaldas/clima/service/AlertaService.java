package edu.ucaldas.clima.service;

import edu.ucaldas.clima.dto.AlertaRequestDTO;
import edu.ucaldas.clima.dto.AlertaResponseDTO;
import edu.ucaldas.clima.exception.RecursoNoEncontradoException;
import edu.ucaldas.clima.model.Alerta;
import edu.ucaldas.clima.model.RegistroClima;
import edu.ucaldas.clima.persistance.AlertaRepository;
import edu.ucaldas.clima.persistance.RegistroClimaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final RegistroClimaRepository registroClimaRepository;

    public AlertaService(
            AlertaRepository alertaRepository,
            RegistroClimaRepository registroClimaRepository
    ) {
        this.alertaRepository = alertaRepository;
        this.registroClimaRepository = registroClimaRepository;
    }

    @Transactional
    public AlertaResponseDTO crear(AlertaRequestDTO request) {
        RegistroClima registroClima = registroClimaRepository.findById(request.registroClimaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Registro climático no encontrado con id " + request.registroClimaId()
                ));

        String estado = request.estado();

        Alerta alerta = new Alerta();
        alerta.setDescripcion(request.descripcion().trim());
        alerta.setNivel(request.nivel().trim());
        alerta.setFecha(request.fecha() != null ? request.fecha() : LocalDateTime.now());
        alerta.setEstado(estado == null || estado.isBlank() ? "ACTIVA" : estado.trim());
        alerta.setRegistroClima(registroClima);

        return AlertaResponseDTO.desde(alertaRepository.save(alerta));
    }

    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> listarPorNivel(String nivel) {
        return alertaRepository.findByNivelIgnoreCase(nivel.trim()).stream()
                .map(AlertaResponseDTO::desde)
                .toList();
    }
}
