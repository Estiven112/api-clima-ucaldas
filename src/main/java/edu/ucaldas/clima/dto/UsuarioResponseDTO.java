package edu.ucaldas.clima.dto;

import edu.ucaldas.clima.model.Usuario;

import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String correo,
        List<RolResponseDTO> roles
) {

    public static UsuarioResponseDTO desde(Usuario usuario) {
        List<RolResponseDTO> roles = usuario.getRoles().stream()
                .map(RolResponseDTO::desde)
                .toList();

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                roles
        );
    }
}
