package edu.ucaldas.clima.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
        name = "estaciones",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_estacion_codigo",
                columnNames = "codigo"
        )
)
public class Estacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Column(name = "ubicacion", nullable = false, length = 200)
    private String ubicacion;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "ciudad_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_estacion_ciudad")
    )
    private Ciudad ciudad;

    @OneToMany(
            mappedBy = "estacion",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<RegistroClima> registros = new ArrayList<>();

    @OneToOne(
            mappedBy = "estacion",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Configuracion configuracion;

    public void agregarRegistro(RegistroClima registro) {
        registros.add(registro);
        registro.setEstacion(this);
    }

    public void asignarConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
        configuracion.setEstacion(this);
    }

    public void eliminarConfiguracion() {
        if (configuracion != null) {
            configuracion.setEstacion(null);
            configuracion = null;
        }
    }
}
