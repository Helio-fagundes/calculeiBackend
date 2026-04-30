package application.calculei.adapters.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ExceptionResponse {

    private Integer status;
    private String erro;
    private String mensagem;
    private LocalDateTime timestamp;

    public ExceptionResponse(Integer status, String erro, String mensagem, LocalDateTime timestamp) {
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }
}
