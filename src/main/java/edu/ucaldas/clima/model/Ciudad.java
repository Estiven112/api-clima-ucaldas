package edu.ucaldas.clima.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "ciudades",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_ciudad_nombre_departamento",
                columnNames = {"nombre", "departamento"}
        )
)
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @Column(name = "poblacion", nullable = false)
    private Long poblacion;

    @OneToMany(
            mappedBy = "ciudad",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<Estacion> estaciones = new ArrayList<>();

    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
        estacion.setCiudad(this);
    }
}
