package application.calculei.domain.valueObject;

import java.time.LocalDate;

public final class DateUtils {

    private DateUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Long businessDays(LocalDate dataInicial, LocalDate dataFinal) {
        int daysInit = Math.min(dataInicial.getDayOfMonth(), 30);
        int daysFinal = Math.min(dataFinal.getDayOfMonth(), 30);

        return Long.valueOf((dataFinal.getYear() - dataInicial.getYear()) * 360 +
                (dataFinal.getMonthValue() - dataInicial.getMonthValue()) * 30 +
                (daysFinal - daysInit));
    }
}
