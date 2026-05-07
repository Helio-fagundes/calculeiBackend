package application.calculei.usecase.simple_interest;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateSimpleInterestTwelve {

    private static final int DAYS_IN_COMMERCIAL_MONTH = 30;
    private static final BigDecimal MONTHLY_RATE = new BigDecimal("0.01");

    public SimpleInterestDto execute(SimpleInterestDto request) {

        long totalDays = DateUtils.businessDays(request.startDate(), request.endDate());
        BigDecimal days = new BigDecimal(totalDays);
        BigDecimal dailyRate = MONTHLY_RATE.divide(BigDecimal.valueOf(DAYS_IN_COMMERCIAL_MONTH), 10, RoundingMode.HALF_UP);

        BigDecimal totalInterest = request.amount().multiply(dailyRate.multiply(days));
        BigDecimal finalAmount = request.amount().add(totalInterest).setScale(2, RoundingMode.HALF_UP);

        return new SimpleInterestDto(finalAmount, request.startDate(), request.endDate());
    }
}
