package application.calculei.adapters.controller;

import application.calculei.usecase.poupanca_nova.CalculatePoupNovaAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateRequest;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poupanca/nova")
public class PoupNovaController {

    private final CalculatePoupNovaAccumulatedValueBetweenDates useCaseCalculatePoupancaBetweenDates;

    public PoupNovaController(CalculatePoupNovaAccumulatedValueBetweenDates useCaseCalculatePoupancaBetweenDates) {
        this.useCaseCalculatePoupancaBetweenDates = useCaseCalculatePoupancaBetweenDates;
    }

    @PostMapping("/calculate/between-dates")
    public CalculatePoupNovaBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculatePoupNovaBetweenDateRequest request){
        return useCaseCalculatePoupancaBetweenDates.calcular(request);
    }
}
