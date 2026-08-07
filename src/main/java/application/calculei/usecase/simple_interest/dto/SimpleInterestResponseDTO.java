package application.calculei.usecase.simple_interest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SimpleInterestResponseDTO(BigDecimal amount, LocalDate startDate, LocalDate endDate, Long businessDays, Long calenderDays) {
}
