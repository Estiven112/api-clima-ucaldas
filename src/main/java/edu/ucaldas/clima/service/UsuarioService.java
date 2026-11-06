package edu.ucaldas.clima.service;

import edu.ucaldas.clima.dto.UsuarioRequestDTO;
import edu.ucaldas.clima.dto.UsuarioResponseDTO;
import edu.ucaldas.clima.exception.RecursoDuplicadoException;
import edu.ucaldas.clima.exception.RecursoNoEncontradoException;
import edu.ucaldas.clima.model.Rol;
import edu.ucaldas.clima.model.Usuario;
import edu.ucaldas.clima.persistance.RolRepository;
import edu.ucaldas.clima.persistance.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional
    public UsuarioResponseDTO crear(UsuarioRequestDTO request) {
        String correo = request.correo().trim().toLowerCase(Locale.ROOT);

        if (usuarioRepository.existsByCorreo(correo)) {
            throw new RecursoDuplicadoException(
                    "Ya existe un usuario con el correo " + correo
            );
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre().trim());
        usuario.setCorreo(correo);
        usuario.setContrasena(passwordEncoder.encode(request.contrasena()));

        return UsuarioResponseDTO.desde(usuarioRepository.save(usuario));
    }

    @Transactional
    public void asignarRol(Long usuarioId, Long rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con id " + usuarioId
                ));

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Rol no encontrado con id " + rolId
                ));

        boolean yaTieneRol = usuario.getRoles().stream()
                .anyMatch(rolActual -> rolActual.getId().equals(rolId));

        if (!yaTieneRol) {
            usuario.agregarRol(rol);
            usuarioRepository.save(usuario);
        }
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarPorRol(String nombre) {
        String nombreRol = nombre.trim();

        if (!rolRepository.existsByNombreIgnoreCase(nombreRol)) {
            throw new RecursoNoEncontradoException(
                    "Rol no encontrado con nombre " + nombreRol
            );
        }

        return usuarioRepository.findUsuariosPorRol(nombreRol).stream()
                .map(UsuarioResponseDTO::desde)
                .toList();
    }
}
