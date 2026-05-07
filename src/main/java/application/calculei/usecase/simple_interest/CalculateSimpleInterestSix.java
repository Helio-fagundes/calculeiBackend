package application.calculei.usecase.simple_interest;

import application.calculei.domain.valueObject.DateUtils;
import application.calculei.usecase.simple_interest.dto.SimpleInterestDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateSimpleInterestSix {

    private static final int DAYS_IN_COMMERCIAL_MONTH = 30;

    public SimpleInterestDto execute(SimpleInterestDto request, BigDecimal interest) {

        long totalDays = DateUtils.businessDays(request.startDate(), request.endDate());

        BigDecimal interestPorcentage = interest.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal days = new BigDecimal(totalDays);

        BigDecimal dailyRate = interestPorcentage
                .divide(BigDecimal.valueOf(DAYS_IN_COMMERCIAL_MONTH), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        BigDecimal totalInterest = request.amount().multiply(dailyRate.multiply(days));

        BigDecimal finalAmount = request.amount().add(totalInterest).setScale(2, RoundingMode.HALF_UP);

        return new SimpleInterestDto(finalAmount ,request.startDate(), request.endDate());
    }
}
