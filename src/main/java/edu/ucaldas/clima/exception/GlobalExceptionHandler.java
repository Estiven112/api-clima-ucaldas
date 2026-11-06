package edu.ucaldas.clima.exception;

import edu.ucaldas.clima.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> manejarNoEncontrado(
            RecursoNoEncontradoException excepcion,
            HttpServletRequest request
    ) {
        return construirRespuesta(HttpStatus.NOT_FOUND, excepcion.getMessage(), request);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErrorResponseDTO> manejarDuplicado(
            RecursoDuplicadoException excepcion,
            HttpServletRequest request
    ) {
        return construirRespuesta(HttpStatus.CONFLICT, excepcion.getMessage(), request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> manejarIntegridad(
            DataIntegrityViolationException excepcion,
            HttpServletRequest request
    ) {
        return construirRespuesta(
                HttpStatus.CONFLICT,
                "La operación viola una restricción de integridad de datos",
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarValidacion(
            MethodArgumentNotValidException excepcion,
            HttpServletRequest request
    ) {
        String mensaje = excepcion.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return construirRespuesta(HttpStatus.BAD_REQUEST, mensaje, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> manejarJsonInvalido(
            HttpMessageNotReadableException excepcion,
            HttpServletRequest request
    ) {
        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "El cuerpo de la solicitud no tiene un formato válido",
                request
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> manejarArgumentoInvalido(
            IllegalArgumentException excepcion,
            HttpServletRequest request
    ) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, excepcion.getMessage(), request);
    }

    private ResponseEntity<ErrorResponseDTO> construirRespuesta(
            HttpStatus estado,
            String mensaje,
            HttpServletRequest request
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                estado.value(),
                estado.getReasonPhrase(),
                mensaje,
                request.getRequestURI()
        );

        return ResponseEntity.status(estado).body(error);
    }
}
