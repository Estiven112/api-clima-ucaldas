package edu.ucaldas.clima.persistance;

import edu.ucaldas.clima.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCorreo(String correo);

    @EntityGraph(attributePaths = "roles")
    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.roles r WHERE LOWER(r.nombre) = LOWER(:nombre)")
    List<Usuario> findUsuariosPorRol(@Param("nombre") String nombre);
}
