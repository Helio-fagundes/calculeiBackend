package application.calculei.domain.dto;

import java.util.Date;

public record ResponseIndicesDTO(
        Long id,
        String nome,
        String status,
        Double fator,
        Double acumulado,
        Double valor,
        Date data,
        Date datafim
) {}
