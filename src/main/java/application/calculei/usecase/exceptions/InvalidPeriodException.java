package application.calculei.usecase.exceptions;

import java.time.LocalDate;

public class InvalidPeriodException extends RuntimeException {
    public InvalidPeriodException(LocalDate startDate, LocalDate endDate) {
        super(String.format(
                "A data de término (%s) deve ser anterior à data de início (%s).",
                startDate,
                endDate
        ));
    }

    public InvalidPeriodException(String message) {
        super(String.format(
                "A data de %s não pode ser posterior à data atual.",
                message
        ));
    }
}
