package application.calculei.adapters.exception.handler;

import application.calculei.adapters.exception.ExceptionResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.exceptions.HistoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BancoCentralDataNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBancoCentralDataNotFoundException(
            BancoCentralDataNotFoundException ex) {

        ExceptionResponse error = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .erro("Dados não encontrados no Banco Central")
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HistoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleHistoryNotFoundException(
            HistoryNotFoundException ex){

        ExceptionResponse error = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .erro("Histórico não encontrado")
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
