package application.calculei.adapters.controller;

import application.calculei.usecase.poupanca_antiga.CalculatePoupAntigoAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateRequest;
import application.calculei.usecase.poupanca_antiga.dto.CalculatePoupAntigoBetweenDateResponse;
import application.calculei.usecase.poupanca_nova.CalculatePoupNovaAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateRequest;
import application.calculei.usecase.poupanca_nova.dto.CalculatePoupNovaBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poupanca")
public class PoupancaController {

    private final CalculatePoupNovaAccumulatedValueBetweenDates useCaseCalculatePoupancaBetweenDates;
    private final CalculatePoupAntigoAccumulatedValueBetweenDates useCaseCalculatePoupancaAntigaBetweenDates;

    public PoupancaController(CalculatePoupNovaAccumulatedValueBetweenDates useCaseCalculatePoupancaBetweenDates, CalculatePoupAntigoAccumulatedValueBetweenDates useCaseCalculatePoupancaAntigaBetweenDates) {
        this.useCaseCalculatePoupancaBetweenDates = useCaseCalculatePoupancaBetweenDates;
        this.useCaseCalculatePoupancaAntigaBetweenDates = useCaseCalculatePoupancaAntigaBetweenDates;
    }

    @PostMapping("/nova/calculate/between-dates")
    public CalculatePoupNovaBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculatePoupNovaBetweenDateRequest request){
        return useCaseCalculatePoupancaBetweenDates.execute(request);
    }

    @PostMapping("/antiga/calculate/between-dates")
    public CalculatePoupAntigoBetweenDateResponse calculateBetweenDateAntiga(@Valid @RequestBody CalculatePoupAntigoBetweenDateRequest request){
        return useCaseCalculatePoupancaAntigaBetweenDates.execute(request);
    }
}
