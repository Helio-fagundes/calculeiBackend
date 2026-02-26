package application.calculei;

import application.calculei.adapters.gateway.cdi.CdiJpaRepository;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.usecase.cdi.CalculateAccumulatedValueBetweenDates;
import application.calculei.usecase.cdi.dto.CalculateBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateBetweenDateResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalculeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculeiApplication.class, args);
    }

    @Bean
    public IndexRepository indexRepository(CdiIndexRepository repo) {
        return new CdiJpaRepository(repo);
    }

    @Bean
    public CalculateAccumulatedValueBetweenDates calculateAccumulatedValueBetweenDates(CdiIndexRepository repo) {
        return new CalculateAccumulatedValueBetweenDates(repo);
    }
}
