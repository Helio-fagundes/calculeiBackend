package application.calculei.adapters.controller;

import application.calculei.usecase.igpm.CalculateIgpmAccumulatedValueBetweenDates;
import application.calculei.usecase.igpm.dto.CalculateIgpmBetweenDateRequest;
import application.calculei.usecase.igpm.dto.CalculateIgpmBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/igpm")
public class IgpmController {

    private final CalculateIgpmAccumulatedValueBetweenDates useCaseCalculateDays;

    public IgpmController(CalculateIgpmAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateIgpmBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIgpmBetweenDateRequest request) {
        return useCaseCalculateDays.calcular(request);
    }
}
