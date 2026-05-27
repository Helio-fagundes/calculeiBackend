package application.calculei.adapters.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
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
