package edu.ucaldas.clima.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record MedicionRequestDTO(
        @NotNull(message = "La temperatura es obligatoria")
        Double temperatura,

        @NotNull(message = "La humedad es obligatoria")
        @Min(value = 0, message = "La humedad no puede ser negativa")
        @Max(value = 100, message = "La humedad no puede superar 100")
        Integer humedad,

        @NotNull(message = "La presión es obligatoria")
        @Positive(message = "La presión debe ser mayor que cero")
        Double presion,

        LocalDateTime fecha,

        @JsonAlias("estacion_id")
        @NotNull(message = "El id de la estación es obligatorio")
        @Positive(message = "El id de la estación debe ser mayor que cero")
        Long estacionId
) {
}
