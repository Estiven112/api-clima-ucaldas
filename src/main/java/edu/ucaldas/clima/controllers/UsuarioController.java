package edu.ucaldas.clima.controllers;

import edu.ucaldas.clima.dto.UsuarioRequestDTO;
import edu.ucaldas.clima.dto.UsuarioResponseDTO;
import edu.ucaldas.clima.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(request));
    }

    @PostMapping("/{id}/roles/{rolId}")
    public ResponseEntity<Void> asignarRol(
            @PathVariable Long id,
            @PathVariable Long rolId
    ) {
        usuarioService.asignarRol(id, rolId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rol/{nombre}")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorRol(
            @PathVariable String nombre
    ) {
        return ResponseEntity.ok(usuarioService.listarPorRol(nombre));
    }
}
