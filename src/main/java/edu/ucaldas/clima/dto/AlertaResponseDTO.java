package edu.ucaldas.clima.dto;

import edu.ucaldas.clima.model.Alerta;
import edu.ucaldas.clima.model.RegistroClima;

import java.time.LocalDateTime;

public record AlertaResponseDTO(
        Long id,
        String descripcion,
        String nivel,
        LocalDateTime fecha,
        String estado,
        Long registroClimaId
) {

    public static AlertaResponseDTO desde(Alerta alerta) {
        RegistroClima registroClima = alerta.getRegistroClima();
        return new AlertaResponseDTO(
                alerta.getId(),
                alerta.getDescripcion(),
                alerta.getNivel(),
                alerta.getFecha(),
                alerta.getEstado(),
                registroClima != null ? registroClima.getId() : null
        );
    }
}
