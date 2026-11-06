package edu.ucaldas.clima.dto;

import edu.ucaldas.clima.model.Rol;

public record RolResponseDTO(
        Long id,
        String nombre,
        String descripcion
) {

    public static RolResponseDTO desde(Rol rol) {
        return new RolResponseDTO(
                rol.getId(),
                rol.getNombre(),
                rol.getDescripcion()
        );
    }
}
