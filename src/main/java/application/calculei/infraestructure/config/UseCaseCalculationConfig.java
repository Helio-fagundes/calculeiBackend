package application.calculei.infraestructure.config;

import application.calculei.domain.repository.HistoryPdfValuePort;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.usecase.cdi.CalculateCdiAccumulatedValueBetweenDates;
import application.calculei.usecase.history_pdf_value.HistoryPdfValueMethod;
import application.calculei.usecase.igpdi.CalculateIgpdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpm.CalculateIgpmAccumulatedValueBetweenDates;
import application.calculei.usecase.indice_bc.IndicesBcUseCase;
import application.calculei.usecase.ipca.CalculateIpcaAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcae.CalculateIpcaeAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_antiga.CalculatePoupAntigoAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_antiga_nova.CalculatePoupNovaAndAntigaAccumulatedValueByPeriod;
import application.calculei.usecase.poupanca_nova.CalculatePoupNovaAccumulatedValueBetweenDates;
import application.calculei.usecase.salario.CalculateSalarioAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.diario.CalculateSelicDiarioAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.mensal.CalculateSelicMensalAccumulatedValueBetweenDates;
import application.calculei.usecase.simple_interest.CalculateInterestByPeriod;
import application.calculei.usecase.simple_interest.CalculateSimpleInterest;
import application.calculei.usecase.taxa_legal.CalculateTaxaLegalAccumulatedValueBetweenDates;
import application.calculei.usecase.tj_11960.CalculateTj11960SelicValueBetweenDates;
import application.calculei.usecase.tj_6899.CalculateTj6899UfirValueBetweenDates;
import application.calculei.usecase.tr.CalculateTrAccumulatedValueBetweenDates;
import application.calculei.usecase.ufir.UfirUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseCalculationConfig {

    @Bean
    public CalculateCdiAccumulatedValueBetweenDates calculateCdiAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryCdi") IndexRepository repo) {
        return new CalculateCdiAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateTaxaLegalAccumulatedValueBetweenDates calculateTaxaLegalAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryTaxaLegal") IndexRepository repo) {
        return new CalculateTaxaLegalAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateTj6899UfirValueBetweenDates calculateTj6899UfirValueBetweenDates(
            @Qualifier("indexRepositoryTj6899") IndexRepository repo) {
        return new CalculateTj6899UfirValueBetweenDates(repo);
    }

    @Bean
    public CalculatePoupAntigoAccumulatedValueBetweenDates calculatePoupAntigoAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryPoupAntiga") IndexRepository repo) {
        return new CalculatePoupAntigoAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateSalarioAccumulatedValueBetweenDates calculateSalarioAccumulatedValueBetweenDates(
            @Qualifier("indexRepositorySalario") IndexRepository repo) {
        return new CalculateSalarioAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculatePoupNovaAccumulatedValueBetweenDates calculatePoupNovaAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryPoupNova") IndexRepository repo) {
        return new CalculatePoupNovaAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIgpdiAccumulatedValueBetweenDates calculateIgpdiAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryIgpdi") IndexRepository repo) {
        return new CalculateIgpdiAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIpcaeAccumulatedValueBetweenDates calculateIpcaeAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryIpcae") IndexRepository repo) {
        return new CalculateIpcaeAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIgpmAccumulatedValueBetweenDates calculateIgpmAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryIgpm") IndexRepository repo) {
        return new CalculateIgpmAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIpcaAccumulatedValueBetweenDates calculateIpcaAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryIpca") IndexRepository repo) {
        return new CalculateIpcaAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateSelicMensalAccumulatedValueBetweenDates calculateSelicAccumulatedValueBetweenDates(
            @Qualifier("indexRepositorySelic") IndexRepository repo) {
        return new CalculateSelicMensalAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateSelicDiarioAccumulatedValueBetweenDates calculateSelicDiarioAccumulatedValueBetweenDates(
            @Qualifier("indexRepositorySelicDiario") IndexRepository selicRepo)
    {
        return new CalculateSelicDiarioAccumulatedValueBetweenDates(selicRepo);
    }

    @Bean
    public CalculateTrAccumulatedValueBetweenDates calculateTrAccumulatedValueBetweenDates(
            @Qualifier("indexRepositoryTr") IndexRepository repo) {
        return new CalculateTrAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateTj11960SelicValueBetweenDates calculateTj11960SelicValue(
            @Qualifier("indexRepositoryTj11960") IndexRepository tj11960Repo,
            @Qualifier("indexRepositorySelic") IndexRepository selicRepo) {
        return new CalculateTj11960SelicValueBetweenDates(tj11960Repo, selicRepo);
    }

    @Bean
    public UfirUseCase ufirUseCase(@Qualifier("indexRepositoryUfir") IndexRepository repository,
                                   IndiceBcPort indiceBcPort) {
        return new UfirUseCase(repository,  indiceBcPort);
    }

    @Bean
    public CalculatePoupNovaAndAntigaAccumulatedValueByPeriod calculatePoupNovaAndAntigaAccumulatedValueByPeriod(
            @Qualifier("indexRepositoryPoupAntiga") IndexRepository poupAntigaRepo,
            @Qualifier("indexRepositoryPoupNova") IndexRepository poupNovaRepo) {
        return new CalculatePoupNovaAndAntigaAccumulatedValueByPeriod(poupAntigaRepo, poupNovaRepo);
    }

    @Bean
    public CalculateSimpleInterest calculateSimpleInterestSix() {
        return new CalculateSimpleInterest();
    }

    @Bean
    public IndicesBcUseCase indicesBcUseCase(IndiceBcPort indiceBcPort) {
        return new IndicesBcUseCase(indiceBcPort);
    }

    @Bean
    public CalculateInterestByPeriod calculateInterestByPeriod() {
        return new CalculateInterestByPeriod();
    }

    @Bean
    public HistoryPdfValueMethod historyPdfValueMethod(HistoryPdfValuePort repo) {
        return new HistoryPdfValueMethod(repo);
    }
}
