package application.calculei.domain.dto;

import java.util.Date;

public record IndexRequestDTO(Long id, String nome, Double valor, Double fator, Date dataInit) {
}
