package application.calculei.usecase.indice_bc.dto;

import java.time.LocalDate;

public record IndicesBcResponseDto(int code , String description, String periodicidade, String urlBC, LocalDate lastUpdate) {
}
