package edu.ucaldas.clima.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_usuario_correo",
                columnNames = "correo"
        )
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 150)
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(
                    name = "usuario_id",
                    foreignKey = @ForeignKey(name = "fk_usuario_rol_usuario")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "rol_id",
                    foreignKey = @ForeignKey(name = "fk_usuario_rol_rol")
            ),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_usuario_rol",
                    columnNames = {"usuario_id", "rol_id"}
            )
    )
    private Set<Rol> roles = new LinkedHashSet<>();

    public void agregarRol(Rol rol) {
        roles.add(rol);
        rol.getUsuarios().add(this);
    }

    public void eliminarRol(Rol rol) {
        roles.remove(rol);
        rol.getUsuarios().remove(this);
    }
}
