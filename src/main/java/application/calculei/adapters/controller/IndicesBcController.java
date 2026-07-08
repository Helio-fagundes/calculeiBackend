package application.calculei.adapters.controller;

import application.calculei.usecase.indice_bc.IndicesBcUseCase;
import application.calculei.usecase.indice_bc.dto.IndicesBcResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/indices")
public class IndicesBcController {

    private final IndicesBcUseCase  indicesBcUseCase;

    public IndicesBcController(IndicesBcUseCase indicesBcUseCase) {
        this.indicesBcUseCase = indicesBcUseCase;
    }

    @GetMapping("/all")
    public List<IndicesBcResponseDto> getAllIndexInCalculei() {
        return indicesBcUseCase.getAllIndexInCalculei();
    }
}
