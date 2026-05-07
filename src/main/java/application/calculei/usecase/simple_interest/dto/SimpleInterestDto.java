package application.calculei.usecase.simple_interest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SimpleInterestDto(BigDecimal amount, LocalDate startDate, LocalDate endDate) {
}
