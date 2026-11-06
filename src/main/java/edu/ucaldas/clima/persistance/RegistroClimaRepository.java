package edu.ucaldas.clima.persistance;

import edu.ucaldas.clima.model.RegistroClima;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroClimaRepository extends JpaRepository<RegistroClima, Long> {

    @EntityGraph(attributePaths = "estacion")
    List<RegistroClima> findByEstacionId(Long estacionId);

    @Query("SELECT r FROM RegistroClima r WHERE r.temperatura > 30")
    List<RegistroClima> findMedicionesAltas();

    @Query("SELECT AVG(r.temperatura) FROM RegistroClima r WHERE r.estacion.ciudad.id = :ciudadId")
    Double findPromedioTemperaturaPorCiudad(@Param("ciudadId") Long ciudadId);
}
