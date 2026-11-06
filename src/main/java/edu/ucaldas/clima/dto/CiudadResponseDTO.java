package edu.ucaldas.clima.dto;

import edu.ucaldas.clima.model.Ciudad;

public record CiudadResponseDTO(
        Long id,
        String nombre,
        String departamento,
        Long poblacion
) {

    public static CiudadResponseDTO desde(Ciudad ciudad) {
        return new CiudadResponseDTO(
                ciudad.getId(),
                ciudad.getNombre(),
                ciudad.getDepartamento(),
                ciudad.getPoblacion()
        );
    }
}
