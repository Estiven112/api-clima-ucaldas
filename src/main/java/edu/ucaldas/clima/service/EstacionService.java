package edu.ucaldas.clima.service;

import edu.ucaldas.clima.dto.EstacionRequestDTO;
import edu.ucaldas.clima.dto.EstacionResponseDTO;
import edu.ucaldas.clima.exception.RecursoDuplicadoException;
import edu.ucaldas.clima.exception.RecursoNoEncontradoException;
import edu.ucaldas.clima.model.Ciudad;
import edu.ucaldas.clima.model.Estacion;
import edu.ucaldas.clima.persistance.CiudadRepository;
import edu.ucaldas.clima.persistance.EstacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstacionService {

    private final EstacionRepository estacionRepository;
    private final CiudadRepository ciudadRepository;

    public EstacionService(
            EstacionRepository estacionRepository,
            CiudadRepository ciudadRepository
    ) {
        this.estacionRepository = estacionRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional
    public EstacionResponseDTO crear(EstacionRequestDTO request) {
        Ciudad ciudad = ciudadRepository.findById(request.ciudadId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Ciudad no encontrada con id " + request.ciudadId()
                ));

        String codigo = request.codigo().trim();

        if (estacionRepository.existsByCodigo(codigo)) {
            throw new RecursoDuplicadoException(
                    "Ya existe una estación con el código " + codigo
            );
        }

        Estacion estacion = new Estacion();
        estacion.setNombre(request.nombre().trim());
        estacion.setCodigo(codigo);
        estacion.setUbicacion(request.ubicacion().trim());
        estacion.setEstado(request.estado().trim());
        estacion.setCiudad(ciudad);

        return EstacionResponseDTO.desde(estacionRepository.save(estacion));
    }

    @Transactional(readOnly = true)
    public List<EstacionResponseDTO> listarPorCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new RecursoNoEncontradoException(
                    "Ciudad no encontrada con id " + ciudadId
            );
        }

        return estacionRepository.findByCiudadId(ciudadId).stream()
                .map(EstacionResponseDTO::desde)
                .toList();
    }
}
