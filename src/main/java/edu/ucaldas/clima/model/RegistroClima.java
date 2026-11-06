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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "registros_clima")
public class RegistroClima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "temperatura", nullable = false)
    private Double temperatura;

    @Column(name = "humedad", nullable = false)
    private Integer humedad;

    @Column(name = "presion", nullable = false)
    private Double presion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "estacion_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_registro_clima_estacion")
    )
    private Estacion estacion;

    @OneToMany(
            mappedBy = "registroClima",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Alerta> alertas = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "registro_tipo_medicion",
            joinColumns = @JoinColumn(
                    name = "registro_clima_id",
                    foreignKey = @ForeignKey(name = "fk_registro_tipo_registro")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "tipo_medicion_id",
                    foreignKey = @ForeignKey(name = "fk_registro_tipo_medicion")
            ),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_registro_tipo_medicion",
                    columnNames = {"registro_clima_id", "tipo_medicion_id"}
            )
    )
    private Set<TipoMedicion> tiposMedicion = new LinkedHashSet<>();

    public void agregarAlerta(Alerta alerta) {
        alertas.add(alerta);
        alerta.setRegistroClima(this);
    }

    public void eliminarAlerta(Alerta alerta) {
        alertas.remove(alerta);
        alerta.setRegistroClima(null);
    }

    public void agregarTipoMedicion(TipoMedicion tipoMedicion) {
        tiposMedicion.add(tipoMedicion);
        tipoMedicion.getRegistros().add(this);
    }

    public void eliminarTipoMedicion(TipoMedicion tipoMedicion) {
        tiposMedicion.remove(tipoMedicion);
        tipoMedicion.getRegistros().remove(this);
    }
}
