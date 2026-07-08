package application.calculei.usecase.indice_bc;

import application.calculei.adapters.mapper.indice_bc.IndiceBcMapper;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.usecase.indice_bc.dto.IndicesBcResponseDto;

import java.util.List;

public class IndicesBcUseCase {

    private final IndiceBcPort indiceBcPort;

    public IndicesBcUseCase(IndiceBcPort indiceBcPort) {
        this.indiceBcPort = indiceBcPort;
    }

    public List<IndicesBcResponseDto> getAllIndexInCalculei() {
        return indiceBcPort.getAll()
                .stream()
                .map(IndiceBcMapper::toResponseDto)
                .toList();
    }
}
