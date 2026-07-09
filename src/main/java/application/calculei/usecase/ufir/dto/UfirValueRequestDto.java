package application.calculei.usecase.ufir.dto;

import java.math.BigDecimal;

public record UfirValueRequestDto(BigDecimal valueUfir, Integer year) {
}
