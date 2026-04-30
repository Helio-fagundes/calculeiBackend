package application.calculei.usecase.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(String.format(
                "Nenhum dado encontrado: %s",
                message
        ));
    }
}
