package application.calculei.adapters.controller;

import application.calculei.usecase.inpc.CalculateInpcAccumulatedValueBetweenDates;
import application.calculei.usecase.inpc.dto.CalculateInpcBetweenDateRequest;
import application.calculei.usecase.inpc.dto.CalculateInpcBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inpc")
public class InpcController {

    private final CalculateInpcAccumulatedValueBetweenDates useCaseCalculateDays;

    public InpcController(CalculateInpcAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateInpcBetweenDateResponse calculateBetweenDate( @Valid @RequestBody CalculateInpcBetweenDateRequest request){
        return useCaseCalculateDays.calcular(request);
    }
}
