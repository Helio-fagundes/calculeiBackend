package application.calculei.adapters.controller;

import application.calculei.usecase.ipcbr.CalculateIpcbrAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcbr.dto.CalculateIpcbrBetweenDateRequest;
import application.calculei.usecase.ipcbr.dto.CalculateIpcbrBetweenDateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ipcbr")
public class IpcbrController {

    private final CalculateIpcbrAccumulatedValueBetweenDates useCaseCalculateDays;

    public IpcbrController(CalculateIpcbrAccumulatedValueBetweenDates useCaseCalculateDays) {
        this.useCaseCalculateDays = useCaseCalculateDays;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateIpcbrBetweenDateResponse calculateBetweenDate(@Valid @RequestBody CalculateIpcbrBetweenDateRequest request){
        return useCaseCalculateDays.calcular(request);
    }
}
