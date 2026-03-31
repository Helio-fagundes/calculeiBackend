package application.calculei.domain.valueObject;

import java.time.LocalDate;

public class DateUtils {

    public static int businessDays(LocalDate dataInicial, LocalDate dataFinal) {
        int daysInit = Math.min(dataInicial.getDayOfMonth(), 30);
        int daysFinal = Math.min(dataFinal.getDayOfMonth(), 30);

        return (dataFinal.getYear() - dataInicial.getYear()) * 360 +
                (dataFinal.getMonthValue() - dataInicial.getMonthValue()) * 30 +
                (daysFinal - daysInit);
    }
}
