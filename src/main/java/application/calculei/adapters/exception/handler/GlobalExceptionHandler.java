package application.calculei.adapters.exception.handler;

import application.calculei.adapters.event.dto.SystemErrorWarning;
import application.calculei.adapters.exception.ExceptionResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ApplicationEventPublisher eventPublisher;

    public GlobalExceptionHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
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

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDataNotFoundException(
            DataNotFoundException ex
    ){
        ExceptionResponse error = ExceptionResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .erro("Dado não encontrado")
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(InvalidPeriodException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPeriodException(
            InvalidPeriodException ex
    ){
        ExceptionResponse error = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Data inválida")
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ExceptionResponse> handleValueInvalidException(
            InvalidValueException ex
    ) {
        ExceptionResponse error = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Valor inválido")
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ValueNullOrEmptyException.class)
    public ResponseEntity<ExceptionResponse> handleValueNullOrEmpty(
            ValueNullOrEmptyException ex
    ){
        ExceptionResponse error = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Valor nulo ou vazio")
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({
            CannotCreateTransactionException.class,
            DataAccessResourceFailureException.class
    })
    public ResponseEntity<ExceptionResponse> handleDatabaseDown(Exception ex, HttpServletRequest request) {
        log.error("ERRO CRÍTICO: Banco de Dados Inacessível \n" + ex.getMessage());

        eventPublisher.publishEvent(new SystemErrorWarning(ex, request));

        ExceptionResponse error = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor",
                "Desculpe, ocorreu uma instabilidade temporária em nossos serviços. Por favor, tente novamente em alguns minutos.",
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex,  HttpServletRequest request) {
        log.error("ERRO DESCONHECIDO: " + ex.getClass().getName() + " - \n" + ex.getMessage());

        eventPublisher.publishEvent(new SystemErrorWarning(ex, request));

        ExceptionResponse error = new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado",
                "Ocorreu um erro interno. Nossa equipe já foi notificada.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(
            NullPointerException ex
    ){
        ExceptionResponse error = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Valor nulo",
                "Um valor necessário para a operação está nulo. Por favor, verifique os dados fornecidos e tente novamente.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            DateTimeParseException.class
    })
    public ResponseEntity<ExceptionResponse> handleDateTimeParseException(
    ){
        ExceptionResponse error = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Formato de data inválida",
                "O formato da data fornecida é inválido. Por favor, utilize o formato 'yyyy-MM-dd' e tente novamente.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoResourceFoundException(
            NoResourceFoundException ex
    ){
        ExceptionResponse error = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                "O recurso solicitado não foi encontrado. Por favor, verifique a URL e tente novamente.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
