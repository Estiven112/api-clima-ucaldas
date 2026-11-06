package edu.ucaldas.clima;

import edu.ucaldas.clima.dto.AlertaRequestDTO;
import edu.ucaldas.clima.dto.AlertaResponseDTO;
import edu.ucaldas.clima.dto.CiudadRequestDTO;
import edu.ucaldas.clima.dto.CiudadResponseDTO;
import edu.ucaldas.clima.dto.EstacionRequestDTO;
import edu.ucaldas.clima.dto.EstacionResponseDTO;
import edu.ucaldas.clima.dto.MedicionRequestDTO;
import edu.ucaldas.clima.dto.MedicionResponseDTO;
import edu.ucaldas.clima.dto.PromedioTemperaturaResponseDTO;
import edu.ucaldas.clima.dto.UsuarioRequestDTO;
import edu.ucaldas.clima.dto.UsuarioResponseDTO;
import edu.ucaldas.clima.exception.RecursoNoEncontradoException;
import edu.ucaldas.clima.model.Rol;
import edu.ucaldas.clima.persistance.RolRepository;
import edu.ucaldas.clima.service.AlertaService;
import edu.ucaldas.clima.service.CiudadService;
import edu.ucaldas.clima.service.EstacionService;
import edu.ucaldas.clima.service.MedicionService;
import edu.ucaldas.clima.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class SistemaMeteorologicoIntegrationTest {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private EstacionService estacionService;

    @Autowired
    private MedicionService medicionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private RolRepository rolRepository;

    @Test
    void flujoCompletoDeGestionMeteorologica() {
        CiudadResponseDTO ciudad = ciudadService.crear(
                new CiudadRequestDTO("Manizales", "Caldas", 450000L)
        );

        EstacionResponseDTO estacion = estacionService.crear(
                new EstacionRequestDTO(
                        "Estacion Centro",
                        "EST-001",
                        "Centro de Manizales",
                        "ACTIVA",
                        ciudad.id()
                )
        );

        MedicionResponseDTO medicion = medicionService.crear(
                new MedicionRequestDTO(32.5, 80, 1013.2, null, estacion.id())
        );

        AlertaResponseDTO alerta = alertaService.crear(
                new AlertaRequestDTO(
                        "Temperatura superior al umbral",
                        "ALTO",
                        null,
                        "ACTIVA",
                        medicion.id()
                )
        );

        PromedioTemperaturaResponseDTO promedio =
                medicionService.obtenerPromedioTemperaturaPorCiudad(ciudad.id());

        assertEquals(32.5, promedio.promedioTemperatura());
        assertEquals(1, estacionService.listarPorCiudad(ciudad.id()).size());
        assertEquals(1, medicionService.listarPorEstacion(estacion.id()).size());
        assertEquals(1, alertaService.listarPorNivel("alto").size());
        assertEquals("ACTIVA", alerta.estado());

        Rol rol = new Rol();
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador del sistema");
        rolRepository.save(rol);

        UsuarioResponseDTO usuario = usuarioService.crear(
                new UsuarioRequestDTO("Ana Perez", "ana@example.com", "secreta")
        );

        usuarioService.asignarRol(usuario.id(), rol.getId());

        assertEquals(1, usuarioService.listarPorRol("ADMIN").size());
        assertEquals(1, usuarioService.listarPorRol("admin").get(0).roles().size());
    }

    @Test
    void retornaErrorCuandoLaCiudadNoExiste() {
        assertThrows(
                RecursoNoEncontradoException.class,
                () -> estacionService.listarPorCiudad(9999L)
        );
    }

    @Test
    void promedioEsCeroCuandoNoHayMediciones() {
        CiudadResponseDTO ciudad = ciudadService.crear(
                new CiudadRequestDTO("Pereira", "Risaralda", 500000L)
        );

        PromedioTemperaturaResponseDTO promedio =
                medicionService.obtenerPromedioTemperaturaPorCiudad(ciudad.id());

        assertEquals(0.0, promedio.promedioTemperatura());
    }
}
