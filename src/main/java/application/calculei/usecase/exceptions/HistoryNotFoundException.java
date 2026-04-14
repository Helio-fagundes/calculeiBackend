package application.calculei.usecase.exceptions;

public class HistoryNotFoundException extends RuntimeException {

    public HistoryNotFoundException(String token) {
        super(String.format(
                "Histórico com token '%s' não encontrado.",
                token
        ));
    }
}
