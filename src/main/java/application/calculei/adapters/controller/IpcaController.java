package application.calculei.adapters.controller;

import application.calculei.usecase.ipca.CalculateIpcaAccumulatedValueBetweenDates;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateRequest;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ipca")
public class IpcaController {

    private final CalculateIpcaAccumulatedValueBetweenDates useCaseCalculateDays;

    public IpcaController(CalculateIpcaAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateIpcaBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIpcaBetweenDateRequest request) {
        return useCaseCalculateDays.execute(request);
    }
}
