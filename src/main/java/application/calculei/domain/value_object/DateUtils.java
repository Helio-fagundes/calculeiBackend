package application.calculei.domain.value_object;

import java.time.LocalDate;

public final class DateUtils {

    private DateUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Long businessDays(LocalDate dataInicial, LocalDate dataFinal) {
        int daysInit = Math.min(dataInicial.getDayOfMonth(), 30);
        int daysFinal = Math.min(dataFinal.getDayOfMonth(), 30);

        return (dataFinal.getYear() - dataInicial.getYear()) * 360L +
                (dataFinal.getMonthValue() - dataInicial.getMonthValue()) * 30L +
                (daysFinal - daysInit);
    }
}
