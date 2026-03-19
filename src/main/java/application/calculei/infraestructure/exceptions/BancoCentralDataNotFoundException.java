package application.calculei.infraestructure.exceptions;

import java.time.LocalDate;

public class BancoCentralDataNotFoundException extends RuntimeException {

    public BancoCentralDataNotFoundException(String sigla, LocalDate dataInicio) {
        super(String.format(
                "[Banco Central] Índice '%s' sem dados a partir de %s.",
                sigla, dataInicio
        ));
    }
}
