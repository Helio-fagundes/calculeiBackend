package application.calculei.adapters.controller;

import application.calculei.usecase.ufir.UfirUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/ufir")
public class UfirController {

    private final UfirUseCase  ufirUseCase;

    public UfirController(UfirUseCase ufirUseCase) {
        this.ufirUseCase = ufirUseCase;
    }

    @GetMapping("/last-value")
    public BigDecimal getLastUfirValue() {
        return ufirUseCase.getLastUfirValue();
    }
}
