package application.calculei.domain.dto;

import java.util.Date;

public record ResponseTj11960(
        Long id,
        Date dataDemonstrativo,
        Date data,
        String nome,
        Double correcaoAcumulada,
        Double valor,
        Double correcao,
        Double fator
) {
}
