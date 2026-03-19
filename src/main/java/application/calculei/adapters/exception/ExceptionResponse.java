package application.calculei.adapters.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ExceptionResponse {

    public Integer status;
    public String erro;
    public String mensagem;
    public LocalDateTime timestamp;

    public ExceptionResponse(Integer status, String erro, String mensagem, LocalDateTime timestamp) {
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }
}
