package application.calculei.usecase.exceptions;

public class ValueNullOrEmptyException extends RuntimeException {
    public ValueNullOrEmptyException(String message) {
        super(String.format("o(a) %s está contendo valor nulo ou vazio.", message));
    }
}
