package application.calculei.domain.dto;

import java.util.Date;

public record ResponseBC(
        Long id,
        Double valor,
        Date dataInit,
        Date dataFim
) {
}
