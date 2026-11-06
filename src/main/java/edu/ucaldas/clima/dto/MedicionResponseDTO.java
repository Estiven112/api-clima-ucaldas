package edu.ucaldas.clima.dto;

import edu.ucaldas.clima.model.Estacion;
import edu.ucaldas.clima.model.RegistroClima;

import java.time.LocalDateTime;

public record MedicionResponseDTO(
        Long id,
        Double temperatura,
        Integer humedad,
        Double presion,
        LocalDateTime fecha,
        Long estacionId,
        String estacionNombre
) {

    public static MedicionResponseDTO desde(RegistroClima registroClima) {
        Estacion estacion = registroClima.getEstacion();
        return new MedicionResponseDTO(
                registroClima.getId(),
                registroClima.getTemperatura(),
                registroClima.getHumedad(),
                registroClima.getPresion(),
                registroClima.getFecha(),
                estacion != null ? estacion.getId() : null,
                estacion != null ? estacion.getNombre() : null
        );
    }
}
