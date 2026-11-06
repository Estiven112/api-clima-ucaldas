package edu.ucaldas.clima.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EstacionRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotBlank(message = "El código es obligatorio")
        @Size(max = 50, message = "El código no puede superar los 50 caracteres")
        String codigo,

        @NotBlank(message = "La ubicación es obligatoria")
        @Size(max = 200, message = "La ubicación no puede superar los 200 caracteres")
        String ubicacion,

        @NotBlank(message = "El estado es obligatorio")
        @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
        String estado,

        @JsonAlias("ciudad_id")
        @NotNull(message = "El id de la ciudad es obligatorio")
        @Positive(message = "El id de la ciudad debe ser mayor que cero")
        Long ciudadId
) {
}
