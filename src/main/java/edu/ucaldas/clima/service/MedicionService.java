package edu.ucaldas.clima.service;

import edu.ucaldas.clima.dto.MedicionRequestDTO;
import edu.ucaldas.clima.dto.MedicionResponseDTO;
import edu.ucaldas.clima.dto.PromedioTemperaturaResponseDTO;
import edu.ucaldas.clima.exception.RecursoNoEncontradoException;
import edu.ucaldas.clima.model.Estacion;
import edu.ucaldas.clima.model.RegistroClima;
import edu.ucaldas.clima.persistance.CiudadRepository;
import edu.ucaldas.clima.persistance.EstacionRepository;
import edu.ucaldas.clima.persistance.RegistroClimaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicionService {

    private final RegistroClimaRepository registroClimaRepository;
    private final EstacionRepository estacionRepository;
    private final CiudadRepository ciudadRepository;

    public MedicionService(
            RegistroClimaRepository registroClimaRepository,
            EstacionRepository estacionRepository,
            CiudadRepository ciudadRepository
    ) {
        this.registroClimaRepository = registroClimaRepository;
        this.estacionRepository = estacionRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional
    public MedicionResponseDTO crear(MedicionRequestDTO request) {
        Estacion estacion = estacionRepository.findById(request.estacionId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Estación no encontrada con id " + request.estacionId()
                ));

        RegistroClima registroClima = new RegistroClima();
        registroClima.setTemperatura(request.temperatura());
        registroClima.setHumedad(request.humedad());
        registroClima.setPresion(request.presion());
        registroClima.setFecha(request.fecha() != null ? request.fecha() : LocalDateTime.now());
        registroClima.setEstacion(estacion);

        return MedicionResponseDTO.desde(registroClimaRepository.save(registroClima));
    }

    @Transactional(readOnly = true)
    public List<MedicionResponseDTO> listarPorEstacion(Long estacionId) {
        if (!estacionRepository.existsById(estacionId)) {
            throw new RecursoNoEncontradoException(
                    "Estación no encontrada con id " + estacionId
            );
        }

        return registroClimaRepository.findByEstacionId(estacionId).stream()
                .map(MedicionResponseDTO::desde)
                .toList();
    }

    @Transactional(readOnly = true)
    public PromedioTemperaturaResponseDTO obtenerPromedioTemperaturaPorCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new RecursoNoEncontradoException(
                    "Ciudad no encontrada con id " + ciudadId
            );
        }

        Double promedio = registroClimaRepository.findPromedioTemperaturaPorCiudad(ciudadId);

        return new PromedioTemperaturaResponseDTO(
                ciudadId,
                promedio != null ? promedio : 0.0
        );
    }
}
