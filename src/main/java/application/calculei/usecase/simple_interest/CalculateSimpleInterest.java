package application.calculei.usecase.simple_interest;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculateSimpleInterest {

    private static final int DAYS_IN_COMMERCIAL_MONTH = 30;

    public SimpleInterestDto execute(SimpleInterestDto request, BigDecimal interest) {

        long totalDays = DateUtils.businessDays(request.startDate(), request.endDate());

        validateDates(request.startDate(), request.endDate());

        validateFactor(interest);

        BigDecimal days = new BigDecimal(totalDays);

        BigDecimal interestPorcentage = calculatePorcentage(interest);

        BigDecimal dailyRate = calculateDailyRate(interestPorcentage);

        BigDecimal totalInterest = request.amount()
                .multiply(dailyRate.multiply(days));

        BigDecimal finalAmount = request.amount()
                .add(totalInterest)
                .setScale(2, RoundingMode.HALF_UP);

        return new SimpleInterestDto(finalAmount ,request.startDate(), request.endDate());
    }

    private BigDecimal calculatePorcentage(BigDecimal interest){
        interest = interest
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        return interest;
    }

    private BigDecimal calculateDailyRate(BigDecimal interestPorcentage){
        return interestPorcentage
                .divide(BigDecimal.valueOf(DAYS_IN_COMMERCIAL_MONTH), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
    }

    private void validateFactor(BigDecimal fator) {
        if (fator.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O Fator deve ser maior que zero");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate){
        if (endDate.isBefore(startDate)){
            throw new InvalidPeriodException(endDate, startDate);
        }

        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data atual.");
        }

        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de término não pode ser posterior à data atual.");
        }
    }
}
