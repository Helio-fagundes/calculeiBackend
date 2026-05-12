package application.calculei;

import application.calculei.adapters.gateway.cdi.CdiJpaRepository;
import application.calculei.adapters.gateway.history_pdf_value.HistoryJpaRepository;
import application.calculei.adapters.gateway.igpdi.IgpdiJpaRepository;
import application.calculei.adapters.gateway.igpm.IgpmJpaRepository;
import application.calculei.adapters.gateway.indice_bc.IndiceBcJpaRepository;
import application.calculei.adapters.gateway.indice_tj_L11960_selic.IndiceTjJpaRepository;
import application.calculei.adapters.gateway.ipca.IpcaJpaRepository;
import application.calculei.adapters.gateway.ipca_e.IpcaeJpaRepository;
import application.calculei.adapters.gateway.poupanca_antiga.PoupAntigaJpaRepository;
import application.calculei.adapters.gateway.poupanca_nova.PoupNovaJpaRepository;
import application.calculei.adapters.gateway.salario.SalarioJpaRepository;
import application.calculei.adapters.gateway.selic.SelicMensalJpaRepository;
import application.calculei.adapters.gateway.taxa_legal.TaxaLegalJpaRepository;
import application.calculei.adapters.gateway.tr.TrJpaRepository;
import application.calculei.adapters.gateway.ufir_Rj.UfirRjJpaRepository;
import application.calculei.adapters.scheduler.SchedulerConfig;
import application.calculei.domain.port.*;
import application.calculei.domain.repository.HistoryPdfValuePort;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.infraestructure.repository.history_pdf_value.HistoryPdfValueRepository;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;
import application.calculei.infraestructure.repository.indice_tj_L11960_selic.TjL11960SelicIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;
import application.calculei.infraestructure.repository.poupanca_antiga.PoupAntigaIndexRepository;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.infraestructure.repository.taxa_legal.TaxaLegalIndexRepository;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;
import application.calculei.infraestructure.repository.ufir_rj.UfirRjIndexRepository;
import application.calculei.usecase.index_monetary_correction.IndexMonetaryCorrection;
import application.calculei.usecase.poupanca_antiga_nova.CalculatePoupNovaAndAntigaAccumulatedValueByPeriod;
import application.calculei.usecase.simple_interest.CalculateInterestByPeriod;
import application.calculei.usecase.simple_interest.CalculateSimpleInterest;
import application.calculei.usecase.tj_11960.CalculateTj11960SelicValueBetweenDates;
import application.calculei.usecase.tj_6899.CalculateTj6899UfirValueBetweenDates;
import application.calculei.usecase.tj_6899.UpdateTj6899FromUfirRj;
import application.calculei.usecase.cdi.CalculateCdiAccumulatedValueBetweenDates;
import application.calculei.usecase.cdi.UpdateCdiFromBc;
import application.calculei.usecase.history_pdf_value.HistoryPdfValueMethod;
import application.calculei.usecase.igpdi.CalculateIgpdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import application.calculei.usecase.igpm.CalculateIgpmAccumulatedValueBetweenDates;
import application.calculei.usecase.igpm.UpdateIgpmFromBc;
import application.calculei.usecase.ipca.CalculateIpcaAccumulatedValueBetweenDates;
import application.calculei.usecase.ipca.UpdateIpcaFromBc;
import application.calculei.usecase.ipcae.CalculateIpcaeAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcae.UpdateIpcaeFromBc;
import application.calculei.usecase.poupanca_antiga.CalculatePoupAntigoAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_antiga.UpdatePoupAntigoFromBc;
import application.calculei.usecase.poupanca_nova.CalculatePoupNovaAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_nova.UpdatePoupNovaFromBc;
import application.calculei.usecase.salario.CalculateSalarioAccumulatedValueBetweenDates;
import application.calculei.usecase.salario.UpdateSalarioFromBc;
import application.calculei.usecase.selic.mensal.CalculateSelicMensalAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.mensal.UpdateSelicMensalFromBc;
import application.calculei.usecase.taxa_legal.CalculateTaxaLegalAccumulatedValueBetweenDates;
import application.calculei.usecase.taxa_legal.UpdateTaxaLegalFromBc;
import application.calculei.usecase.tr.CalculateTrAccumulatedValueBetweenDates;
import application.calculei.usecase.tr.UpdateTrFromBc;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public IndexRepository indexRepositoryCdi(
            CdiIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new CdiJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryTaxaLegal(
            TaxaLegalIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new TaxaLegalJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryTj6899(
            UfirRjIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new UfirRjJpaRepository(repo,  indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryTj11960(
            TjL11960SelicIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new IndiceTjJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIpca(
            IpcaIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new IpcaJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositorySalario(
            SalarioIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new SalarioJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryPoupAntiga(
            PoupAntigaIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new PoupAntigaJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryTr(
            TrIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new TrJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryPoupNova(
            PoupNovaIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new PoupNovaJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositorySelic(
            SelicMensalIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new SelicMensalJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIgpdi(
            IgpdiIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new IgpdiJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIpcae(
            IpcaeIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new IpcaeJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIgpm(
            IgpmIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new IgpmJpaRepository(repo, indicesBcIndexRepository);
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
            BuscarIgpdiFromBcPort indexRepositoryIgpdi,
            @Qualifier("indexRepositoryIgpdi") IndexRepository indexRepository) {

        return new UpdateIgpdiFromBc(indexRepositoryIgpdi, indexRepository);
    }

    @Bean
    public UpdateTaxaLegalFromBc updateTaxaLegalFromBc(
            BuscarTaxaLegalFromBcPort buscarTaxaLegalFromBcPort,
            @Qualifier("indexRepositoryTaxaLegal") IndexRepository repository){
        return new UpdateTaxaLegalFromBc(
                buscarTaxaLegalFromBcPort,
                repository);
    }

    @Bean
    public UpdateSalarioFromBc updateSalarioFromBc(
            BuscarSalarioFromBcPort buscarSalarioFromBcPort,
            @Qualifier("indexRepositorySalario") IndexRepository indexRepository) {

        return new UpdateSalarioFromBc(buscarSalarioFromBcPort, indexRepository);
    }

    @Bean
    public UpdatePoupNovaFromBc updatePoupNovaFromBc(
            @Qualifier("indexRepositoryPoupNova") IndexRepository repository,
            BuscarPoupNovaFromBcPort buscarPoupNovaFromBcPort) {

        return new UpdatePoupNovaFromBc(repository, buscarPoupNovaFromBcPort);
    }

    @Bean
    public UpdateIpcaFromBc updateIpcaFromBc(
            BuscarIpcaFromBcPort buscarIpcaFromBcPort,
            @Qualifier("indexRepositoryIpca") IndexRepository indexRepository) {

        return new UpdateIpcaFromBc(buscarIpcaFromBcPort, indexRepository);
    }

    @Bean
    public UpdateIpcaeFromBc updateIpcaeFromBc(
            @Qualifier("indexRepositoryIpcae") IndexRepository indexRepository,
            BuscarIpcaeFromBcPort buscarIpcaeFromBcPort) {

        return new UpdateIpcaeFromBc(indexRepository, buscarIpcaeFromBcPort);
    }

    @Bean
    public UpdateIgpmFromBc updateIgpmFromBc(
            BuscarIgpmFromBcPort buscarIgpmFromBcPort,
            @Qualifier("indexRepositoryIgpm") IndexRepository indexRepository) {

        return new UpdateIgpmFromBc(buscarIgpmFromBcPort, indexRepository);
    }

    @Bean
    public UpdateCdiFromBc updateCdiFromBc(
            BuscarCdiFromBcPort buscarCdiFromBcPort,
            @Qualifier("indexRepositoryCdi") IndexRepository indexRepository
    ) {

        return new UpdateCdiFromBc(buscarCdiFromBcPort, indexRepository);
    }

    @Bean
    public UpdateSelicMensalFromBc updateSelicFromBc(
            BuscarSelicMensalFromBcPort buscarSelicMensalFromBcPort,
            @Qualifier("indexRepositorySelic") IndexRepository indexRepository) {

        return new UpdateSelicMensalFromBc(buscarSelicMensalFromBcPort, indexRepository);
    }

    @Bean
    public UpdateTrFromBc updateTrFromBc(
            BuscarTrFromBcPort buscarTrFromBcPort,
            @Qualifier("indexRepositoryTr") IndexRepository repository) {
        return new UpdateTrFromBc(
                buscarTrFromBcPort,
                repository);
    }

    @Bean
    public UpdatePoupAntigoFromBc updatePoupAntigoFromBc(
            BuscarPoupAntigoFromBcPort buscarPoupAntigoFromBcPort,
            @Qualifier("indexRepositoryPoupAntiga") IndexRepository repository){
        return new UpdatePoupAntigoFromBc(
                buscarPoupAntigoFromBcPort,
                repository);
    }

    @Bean
    public UpdateTj6899FromUfirRj  updateTj6899FromUfirRj(
            @Qualifier("indexRepositoryTj6899") IndexRepository tjRepository,
            @Qualifier("indexRepositoryTj6899") IndexRepository UfirRjRepository) {
        return new UpdateTj6899FromUfirRj(
                tjRepository,
                UfirRjRepository
        );
    }


    @Bean
    public SchedulerConfig schedulerConfigd(UpdateIgpdiFromBc useCaseIgpdi,
                                            UpdateIpcaeFromBc useCaseIpcae,
                                            UpdateIgpmFromBc useCaseIgpm,
                                            UpdateIpcaFromBc useCaseIpca,
                                            UpdateCdiFromBc useCaseCdi,
                                            UpdateSelicMensalFromBc useCaseSelic,
                                            UpdateTrFromBc useCaseTr,
                                            UpdatePoupNovaFromBc useCasePoupNova,
                                            UpdateSalarioFromBc useCaseSalario,
                                            UpdatePoupAntigoFromBc useCasePoupAntiga,
                                            UpdateTaxaLegalFromBc useCaseTaxaLegal,
                                            UpdateTj6899FromUfirRj updateTj6899FromUfirRj) {
        return new SchedulerConfig(
                useCaseIgpdi,
                useCaseIpcae,
                useCaseIgpm,
                useCaseIpca,
                useCaseCdi,
                useCaseSelic,
                useCaseTr,
                useCasePoupNova,
                useCaseSalario,
                useCasePoupAntiga,
                useCaseTaxaLegal,
                updateTj6899FromUfirRj);
    }

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
    public CalculatePoupNovaAndAntigaAccumulatedValueByPeriod  calculatePoupNovaAndAntigaAccumulatedValueByPeriod(
            @Qualifier("indexRepositoryPoupAntiga") IndexRepository poupAntigaRepo,
            @Qualifier("indexRepositoryPoupNova") IndexRepository poupNovaRepo) {
        return new CalculatePoupNovaAndAntigaAccumulatedValueByPeriod(poupAntigaRepo, poupNovaRepo);
     }

    @Bean
    public CalculateSimpleInterest calculateSimpleInterestSix() {
        return new CalculateSimpleInterest();
    }

    @Bean
    public CalculateInterestByPeriod  calculateInterestByPeriod() {
        return new CalculateInterestByPeriod();
    }

    @Bean
    public HistoryPdfValuePort historyPdfValuePort(HistoryPdfValueRepository repo) {
        return new HistoryJpaRepository(repo);
    }

    @Bean
    public HistoryPdfValueMethod historyPdfValueMethod(HistoryPdfValuePort repo) {
        return new HistoryPdfValueMethod(repo);
    }

    @Bean
    public IndexMonetaryCorrection indexMonetaryCorrection() {
        return new IndexMonetaryCorrection();
    }

}
