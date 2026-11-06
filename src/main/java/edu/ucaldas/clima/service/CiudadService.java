package edu.ucaldas.clima.service;

import edu.ucaldas.clima.dto.CiudadRequestDTO;
import edu.ucaldas.clima.dto.CiudadResponseDTO;
import edu.ucaldas.clima.model.Ciudad;
import edu.ucaldas.clima.persistance.CiudadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CiudadService {

    private final CiudadRepository ciudadRepository;

    public CiudadService(CiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    @Transactional(readOnly = true)
    public List<CiudadResponseDTO> listar() {
        return ciudadRepository.findAll().stream()
                .map(CiudadResponseDTO::desde)
                .toList();
    }

    @Transactional
    public CiudadResponseDTO crear(CiudadRequestDTO request) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(request.nombre().trim());
        ciudad.setDepartamento(request.departamento().trim());
        ciudad.setPoblacion(request.poblacion());

        return CiudadResponseDTO.desde(ciudadRepository.save(ciudad));
    }
}
