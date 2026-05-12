package application.calculei.usecase.simple_interest;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculateInterestByPeriod {

    private static final LocalDate CUT_OFF_DATE = LocalDate.of(2003, 1, 10);

    private static final BigDecimal DAILY_RATE_SIX = BigDecimal.valueOf(0.06)
            .divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);

    private static final BigDecimal DAILY_RATE_TWELVE = BigDecimal.valueOf(0.12)
            .divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP);

    public SimpleInterestDto execute(SimpleInterestDto request) {
        validate(request.startDate(), request.endDate());

        BigDecimal totalInterestPercentage = calculateHybridPercentage(
                request.startDate(),
                request.endDate()
        );

        BigDecimal interestValue = request.amount()
                .multiply(totalInterestPercentage)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal finalAmount = request.amount()
                .add(interestValue)
                .setScale(2, RoundingMode.HALF_UP);

        return new SimpleInterestDto(finalAmount, request.startDate(), request.endDate());
    }

    private BigDecimal calculateHybridPercentage(LocalDate startDate, LocalDate endDate) {

        if (endDate.isBefore(CUT_OFF_DATE)) {
            return calculateProRata(startDate, endDate, DAILY_RATE_SIX);
        }

        if (!startDate.isBefore(CUT_OFF_DATE)) {
            return calculateProRata(startDate, endDate, DAILY_RATE_TWELVE);
        }

        BigDecimal firstPart = calculateProRata(
                startDate,
                CUT_OFF_DATE,
                DAILY_RATE_SIX
        );

        BigDecimal secondPart = calculateProRata(
                CUT_OFF_DATE.plusDays(1),
                endDate,
                DAILY_RATE_TWELVE
        );

        return firstPart.add(secondPart);
    }

    private BigDecimal calculateProRata(LocalDate start, LocalDate end, BigDecimal dailyRate) {
        long totalDays = DateUtils.businessDays(start, end);

        return BigDecimal.valueOf(totalDays).multiply(dailyRate);
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPeriodException(startDate, endDate);
        }
    }
}
