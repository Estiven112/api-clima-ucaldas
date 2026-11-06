package edu.ucaldas.clima.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AlertaRequestDTO(
        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion,

        @NotBlank(message = "El nivel es obligatorio")
        @Size(max = 30, message = "El nivel no puede superar los 30 caracteres")
        String nivel,

        LocalDateTime fecha,

        @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
        String estado,

        @JsonAlias("registro_clima_id")
        @NotNull(message = "El id del registro climático es obligatorio")
        @Positive(message = "El id del registro climático debe ser mayor que cero")
        Long registroClimaId
) {
}
