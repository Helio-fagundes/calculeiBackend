package application.calculei.adapters.controller;

import application.calculei.usecase.salario.CalculateSalarioAccumulatedValueBetweenDates;
import application.calculei.usecase.salario.dto.CalculateSalarioBetweenDateRequest;
import application.calculei.usecase.salario.dto.CalculateSalarioBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salario")
public class SalarioController {

    private final CalculateSalarioAccumulatedValueBetweenDates calculateSalarioAccumulatedValueBetweenDates;

    public SalarioController(CalculateSalarioAccumulatedValueBetweenDates calculateSalarioAccumulatedValueBetweenDates) {
        this.calculateSalarioAccumulatedValueBetweenDates = calculateSalarioAccumulatedValueBetweenDates;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateSalarioBetweenDateResponse calculateSalarioBetweenDateResponse(@Valid @RequestBody CalculateSalarioBetweenDateRequest request){
        return calculateSalarioAccumulatedValueBetweenDates.calcular(request);
    }
}
