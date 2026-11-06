package edu.ucaldas.clima.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CiudadRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotBlank(message = "El departamento es obligatorio")
        @Size(max = 100, message = "El departamento no puede superar los 100 caracteres")
        String departamento,

        @NotNull(message = "La población es obligatoria")
        @Positive(message = "La población debe ser mayor que cero")
        Long poblacion
) {
}
