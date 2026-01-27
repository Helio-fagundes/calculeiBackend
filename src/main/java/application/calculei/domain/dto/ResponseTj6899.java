package application.calculei.domain.dto;

import java.util.Date;

public record ResponseTj6899(
        Long id,
        Date dataDemonstrativa,
        Date data,
        String nome,
        Double correcaoAcumulada,
        Double valor,
        Double correcao,
        Double fator
) {
}
