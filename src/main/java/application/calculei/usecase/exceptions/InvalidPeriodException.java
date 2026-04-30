package application.calculei.usecase.exceptions;

import java.time.LocalDate;

public class InvalidPeriodException extends RuntimeException {
    public InvalidPeriodException(LocalDate startDate, LocalDate endDate) {
        super(String.format(
                "Período inválido: a data de início (%s) deve ser anterior à data de término (%s).",
                startDate,
                endDate
        ));
    }
}
