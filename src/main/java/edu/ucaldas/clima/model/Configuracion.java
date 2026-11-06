package edu.ucaldas.clima.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "configuraciones")
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "intervalo_medicion_minutos", nullable = false)
    private Integer intervaloMedicionMinutos;

    @Column(name = "unidad_temperatura", nullable = false, length = 20)
    private String unidadTemperatura;

    @Column(name = "alertas_habilitadas", nullable = false)
    private Boolean alertasHabilitadas;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "estacion_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_configuracion_estacion")
    )
    private Estacion estacion;
}
