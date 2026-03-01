package application.calculei;

import application.calculei.adapters.gateway.cdi.CdiJpaRepository;
import application.calculei.adapters.gateway.igpdi.IgpdiJpaRepository;
import application.calculei.adapters.gateway.indice_bc.IndiceBcJpaRepository;
import application.calculei.adapters.scheduler.SchedulerConfig;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.usecase.cdi.CalculateCdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpdi.CalculateIgpdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import application.calculei.usecase.igpdi.port.BuscarIgpdiNoBcPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class CalculeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculeiApplication.class, args);
    }

    @Bean
    public IndexRepository indexRepositoryCdi(CdiIndexRepository repo) {
        return new CdiJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryIgpdi(IgpdiIndexRepository repo){
        return new IgpdiJpaRepository(repo);
    }

    @Bean
    public IndiceBcJpaRepository indiceBcRepository(IndicesBcIndexRepository repo) {
        return new IndiceBcJpaRepository(repo) {
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UpdateIgpdiFromBc updateIgpdiFromBc(
            BuscarIgpdiNoBcPort indexRepositoryIgpdi,
            IgpdiIndexRepository igpdiIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateIgpdiFromBc(indexRepositoryIgpdi, igpdiIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public SchedulerConfig igpdiScheduler(UpdateIgpdiFromBc useCase) {
        return new SchedulerConfig(useCase);
    }

    @Bean
    public CalculateCdiAccumulatedValueBetweenDates calculateAccumulatedValueBetweenDates(CdiIndexRepository repo) {
        return new CalculateCdiAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIgpdiAccumulatedValueBetweenDates calculateIgpdiAccumulatedValueBetweenDates(IgpdiIndexRepository repo){
        return new CalculateIgpdiAccumulatedValueBetweenDates(repo);
    }
}
