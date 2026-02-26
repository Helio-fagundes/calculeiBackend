package application.calculei.adapters.controller;

import application.calculei.usecase.cdi.CalculateCdiAccumulatedValueBetweenDates;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cdi")
public class CdiController {

    private final CalculateCdiAccumulatedValueBetweenDates useCaseCalculateDays;

    public CdiController(CalculateCdiAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateCdiBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateCdiBetweenDateRequest request){
        return useCaseCalculateDays.calcular(request);
    }
}
