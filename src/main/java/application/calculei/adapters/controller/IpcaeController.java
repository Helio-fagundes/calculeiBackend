package application.calculei.adapters.controller;

import application.calculei.usecase.ipcae.CalculateIpcaeAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateRequest;
import application.calculei.usecase.ipcae.dto.CalculateIpcaeBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ipcae")
public class IpcaeController {

    private final CalculateIpcaeAccumulatedValueBetweenDates useCaseCalculateDays;

    public IpcaeController(CalculateIpcaeAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateIpcaeBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIpcaeBetweenDateRequest request){
        return useCaseCalculateDays.execute(request);
    }
}
