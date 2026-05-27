package application.calculei.usecase.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException() {
        super("O valor para a correção monetária deve ser superior a 0.");
    }
}
