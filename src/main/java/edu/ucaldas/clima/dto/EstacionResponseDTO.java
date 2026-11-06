package edu.ucaldas.clima.dto;

import edu.ucaldas.clima.model.Ciudad;
import edu.ucaldas.clima.model.Estacion;

public record EstacionResponseDTO(
        Long id,
        String nombre,
        String codigo,
        String ubicacion,
        String estado,
        Long ciudadId,
        String ciudadNombre
) {

    public static EstacionResponseDTO desde(Estacion estacion) {
        Ciudad ciudad = estacion.getCiudad();
        return new EstacionResponseDTO(
                estacion.getId(),
                estacion.getNombre(),
                estacion.getCodigo(),
                estacion.getUbicacion(),
                estacion.getEstado(),
                ciudad != null ? ciudad.getId() : null,
                ciudad != null ? ciudad.getNombre() : null
        );
    }
}
