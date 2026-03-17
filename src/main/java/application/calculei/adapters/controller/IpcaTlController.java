package application.calculei.adapters.controller;

import application.calculei.usecase.ipca_tl.CalculateIpcaTlAccumulatedValueBetweenDates;
import application.calculei.usecase.ipca_tl.dto.CalculateIpcaTlBetweenDateRequest;
import application.calculei.usecase.ipca_tl.dto.CalculateIpcaTlBetweenDateResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ipcatl")
public class IpcaTlController {

    private final CalculateIpcaTlAccumulatedValueBetweenDates ipcaTlAccumulatedValueBetweenDates;

    public IpcaTlController(CalculateIpcaTlAccumulatedValueBetweenDates ipcaTlAccumulatedValueBetweenDates) {
        this.ipcaTlAccumulatedValueBetweenDates = ipcaTlAccumulatedValueBetweenDates;
    }

    @PostMapping("/calculate/between-dates")
    public CalculateIpcaTlBetweenDateResponse calculateIpcaTlBetweenDateResponse(CalculateIpcaTlBetweenDateRequest request){
        return ipcaTlAccumulatedValueBetweenDates.calcular(request);
    }
}
