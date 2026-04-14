package application.calculei;

import application.calculei.adapters.gateway.cdi.CdiJpaRepository;
import application.calculei.adapters.gateway.history_pdf_value.HistoryJpaRepository;
import application.calculei.adapters.gateway.igpdi.IgpdiJpaRepository;
import application.calculei.adapters.gateway.igpm.IgpmJpaRepository;
import application.calculei.adapters.gateway.indice_bc.IndiceBcJpaRepository;
import application.calculei.adapters.gateway.ipca.IpcaJpaRepository;
import application.calculei.adapters.gateway.ipca_15.Ipca15JpaRepository;
import application.calculei.adapters.gateway.ipca_e.IpcaeJpaRepository;
import application.calculei.adapters.gateway.ipca_tl.IpcatlJpaRepository;
import application.calculei.adapters.gateway.poupanca_antiga.PoupAntigaJpaRepository;
import application.calculei.adapters.gateway.poupanca_nova.PoupNovaJpaRepository;
import application.calculei.adapters.gateway.salario.SalarioJpaRepository;
import application.calculei.adapters.gateway.selic.SelicMensalJpaRepository;
import application.calculei.adapters.gateway.taxa_legal.TaxaLegalJpaRepository;
import application.calculei.adapters.gateway.tbf.TbfJpaRepository;
import application.calculei.adapters.gateway.tr.TrJpaRepository;
import application.calculei.adapters.gateway.ufir_Rj.UfirRjJpaRepository;
import application.calculei.adapters.scheduler.SchedulerConfig;
import application.calculei.domain.repository.HistoryPdfValuePort;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.infraestructure.repository.history_pdf_value.HistoryPdfValueRepository;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;
import application.calculei.infraestructure.repository.indice_tj_L6899.TjL6899IndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;
import application.calculei.infraestructure.repository.ipca_15.Ipca15IndexRepository;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;
import application.calculei.infraestructure.repository.ipca_tl.IpcaTlIndexRepository;
import application.calculei.infraestructure.repository.poupanca_antiga.PoupAntigaIndexRepository;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.infraestructure.repository.taxa_legal.TaxaLegalIndexRepository;
import application.calculei.infraestructure.repository.tbf.TbfIndexRepository;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;
import application.calculei.infraestructure.repository.ufir_rj.UfirRjIndexRepository;
import application.calculei.usecase.tj_6899.CalculateTj6899UfirValueBetweenDates;
import application.calculei.usecase.tj_6899.UpdateTj6899FromUfirRj;
import application.calculei.usecase.cdi.CalculateCdiAccumulatedValueBetweenDates;
import application.calculei.usecase.cdi.UpdateCdiFromBc;
import application.calculei.usecase.cdi.port.BuscarCdiFromBcPort;
import application.calculei.usecase.history_pdf_value.HistoryPdfValueMethod;
import application.calculei.usecase.igpdi.CalculateIgpdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import application.calculei.usecase.igpdi.port.BuscarIgpdiFromBcPort;
import application.calculei.usecase.igpm.CalculateIgpmAccumulatedValueBetweenDates;
import application.calculei.usecase.igpm.UpdateIgpmFromBc;
import application.calculei.usecase.igpm.port.BuscarIgpmFromBcPort;
import application.calculei.usecase.ipca.CalculateIpcaAccumulatedValueBetweenDates;
import application.calculei.usecase.ipca.UpdateIpcaFromBc;
import application.calculei.usecase.ipca.port.BuscarIpcaFromBcPort;
import application.calculei.usecase.ipca15.CalculateIpca15AccumulatedValueBetweenDates;
import application.calculei.usecase.ipca15.UpdateIpca15FromBc;
import application.calculei.usecase.ipca15.port.BuscarIpca15FromBcPort;
import application.calculei.usecase.ipca_tl.CalculateIpcaTlAccumulatedValueBetweenDates;
import application.calculei.usecase.ipca_tl.UpdateIpcaTlFromBc;
import application.calculei.usecase.ipca_tl.port.BuscarIpcaTlFromBcPort;
import application.calculei.usecase.ipcae.CalculateIpcaeAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcae.UpdateIpcaeFromBc;
import application.calculei.usecase.ipcae.port.BuscarIpcaeFromBcPort;
import application.calculei.usecase.poupanca_antiga.CalculatePoupAntigoAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_antiga.UpdatePoupAntigoFromBc;
import application.calculei.usecase.poupanca_antiga.port.BuscarPoupAntigoFromBcPort;
import application.calculei.usecase.poupanca_nova.CalculatePoupNovaAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_nova.UpdatePoupNovaFromBc;
import application.calculei.usecase.poupanca_nova.port.BuscarPoupNovaFromBcPort;
import application.calculei.usecase.salario.CalculateSalarioAccumulatedValueBetweenDates;
import application.calculei.usecase.salario.UpdateSalarioFromBc;
import application.calculei.usecase.salario.port.BuscarSalarioFromBcPort;
import application.calculei.usecase.selic.mensal.CalculateSelicMensalAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.mensal.UpdateSelicMensalFromBc;
import application.calculei.usecase.selic.mensal.port.BuscarSelicMensalFromBcPort;
import application.calculei.usecase.taxa_legal.CalculateTaxaLegalAccumulatedValueBetweenDates;
import application.calculei.usecase.taxa_legal.UpdateTaxaLegalFromBc;
import application.calculei.usecase.taxa_legal.port.BuscarTaxaLegalFromBcPort;
import application.calculei.usecase.tbf.CalculateTbfAccumulatedValueBetweenDates;
import application.calculei.usecase.tbf.UpdateTbfFromBc;
import application.calculei.usecase.tbf.port.BuscarTbfFromBcPort;
import application.calculei.usecase.tr.CalculateTrAccumulatedValueBetweenDates;
import application.calculei.usecase.tr.UpdateTrFromBc;
import application.calculei.usecase.tr.port.BuscarTrFromBcPort;
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
    public IndexRepository indexRepositoryTaxaLegal(TaxaLegalIndexRepository repo){
        return new TaxaLegalJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryTj6899(UfirRjIndexRepository repo){
        return new UfirRjJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryIpcaTl(IpcaTlIndexRepository repo){
        return new IpcatlJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryIpca(IpcaIndexRepository repo){
        return new IpcaJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositorySalario(SalarioIndexRepository repo){
        return new SalarioJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryPoupAntiga(PoupAntigaIndexRepository repo){
        return new PoupAntigaJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryTr(TrIndexRepository repo){
        return new TrJpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositoryPoupNova(PoupNovaIndexRepository repo){
        return new PoupNovaJpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositorySelic(SelicMensalIndexRepository repo){
        return new SelicMensalJpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositoryIgpdi(IgpdiIndexRepository repo){
        return new IgpdiJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryIpcae(IpcaeIndexRepository repo){
        return new IpcaeJpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositoryIpca15(Ipca15IndexRepository repo){
        return new Ipca15JpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositoryTbf(TbfIndexRepository repo){
        return new TbfJpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositoryIgpm(IgpmIndexRepository repo){
        return new IgpmJpaRepository(repo);
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
            IgpdiIndexRepository igpdiIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateIgpdiFromBc(indexRepositoryIgpdi, igpdiIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public UpdateTaxaLegalFromBc updateTaxaLegalFromBc(
            BuscarTaxaLegalFromBcPort buscarTaxaLegalFromBcPort,
            TaxaLegalIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new UpdateTaxaLegalFromBc(
                buscarTaxaLegalFromBcPort,
                repository,
                indicesBcIndexRepository
        );
    }

    @Bean
    public UpdateIpcaTlFromBc updateIpcaTlFromBc(
            BuscarIpcaTlFromBcPort buscarIpcaTlFromBcPort,
            IpcaTlIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new UpdateIpcaTlFromBc(
                buscarIpcaTlFromBcPort,
                repository,
                indicesBcIndexRepository
        );
    }

    @Bean
    public UpdateSalarioFromBc updateSalarioFromBc(
            BuscarSalarioFromBcPort buscarSalarioFromBcPort,
            SalarioIndexRepository salarioIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateSalarioFromBc(buscarSalarioFromBcPort, salarioIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public UpdatePoupNovaFromBc updatePoupNovaFromBc(
            PoupNovaIndexRepository repository,
            BuscarPoupNovaFromBcPort buscarPoupNovaFromBcPort,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdatePoupNovaFromBc(repository, buscarPoupNovaFromBcPort, indicesBcIndexRepository);
    }

    @Bean
    public UpdateIpcaFromBc updateIpcaFromBc(
            BuscarIpcaFromBcPort buscarIpcaFromBcPort,
            IpcaIndexRepository ipcaIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateIpcaFromBc(buscarIpcaFromBcPort, ipcaIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public UpdateIpcaeFromBc updateIpcaeFromBc(
            IpcaeIndexRepository ipcaeIndexRepository,
            BuscarIpcaeFromBcPort buscarIpcaeFromBcPort,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateIpcaeFromBc(ipcaeIndexRepository, buscarIpcaeFromBcPort, indicesBcIndexRepository);
    }

    @Bean
    public UpdateIgpmFromBc updateIgpmFromBc(
            BuscarIgpmFromBcPort buscarIgpmFromBcPort,
            IgpmIndexRepository igpmIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateIgpmFromBc(buscarIgpmFromBcPort, igpmIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public UpdateIpca15FromBc updateIpca15FromBc(
            BuscarIpca15FromBcPort buscarIpca15FromBcPort,
            Ipca15IndexRepository ipca15IndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateIpca15FromBc(buscarIpca15FromBcPort, ipca15IndexRepository ,indicesBcIndexRepository);
    }

    @Bean
    public UpdateCdiFromBc updateCdiFromBc(
            BuscarCdiFromBcPort buscarCdiFromBcPort,
            CdiIndexRepository cdiIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateCdiFromBc(buscarCdiFromBcPort, cdiIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public UpdateSelicMensalFromBc updateSelicFromBc(
            BuscarSelicMensalFromBcPort buscarSelicMensalFromBcPort,
            SelicMensalIndexRepository selicMensalIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateSelicMensalFromBc(buscarSelicMensalFromBcPort, selicMensalIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public UpdateTrFromBc updateTrFromBc(
            BuscarTrFromBcPort buscarTrFromBcPort,
            TrIndexRepository trIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new UpdateTrFromBc(
                buscarTrFromBcPort,
                trIndexRepository,
                indicesBcIndexRepository);
    }

    @Bean
    public UpdateTbfFromBc updateTbfFromBc(
            BuscarTbfFromBcPort buscarTbfFromBcPort,
            TbfIndexRepository tbfIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new UpdateTbfFromBc(
                buscarTbfFromBcPort,
                tbfIndexRepository,
                indicesBcIndexRepository);
    }

    @Bean
    public UpdatePoupAntigoFromBc updatePoupAntigoFromBc(
            BuscarPoupAntigoFromBcPort buscarPoupAntigoFromBcPort,
            PoupAntigaIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new UpdatePoupAntigoFromBc(
                buscarPoupAntigoFromBcPort,
                repository,
                indicesBcIndexRepository);
    }

    @Bean
    public UpdateTj6899FromUfirRj  updateTj6899FromUfirRj(
            UfirRjIndexRepository ufirRjIndexRepository,
            TjL6899IndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new UpdateTj6899FromUfirRj(
                ufirRjIndexRepository,
                repository,
                indicesBcIndexRepository
        );
    }


    @Bean
    public SchedulerConfig schedulerConfigd(UpdateIgpdiFromBc useCaseIgpdi,
                                            UpdateIpcaeFromBc useCaseIpcae,
                                            UpdateIpca15FromBc useCaseIpca15,
                                            UpdateIgpmFromBc useCaseIgpm,
                                            UpdateIpcaFromBc useCaseIpca,
                                            UpdateCdiFromBc useCaseCdi,
                                            UpdateSelicMensalFromBc useCaseSelic,
                                            UpdateTrFromBc useCaseTr,
                                            UpdateTbfFromBc useCaseTbf,
                                            UpdatePoupNovaFromBc useCasePoupNova,
                                            UpdateSalarioFromBc useCaseSalario,
                                            UpdatePoupAntigoFromBc useCasePoupAntiga,
                                            UpdateIpcaTlFromBc useCaseIpcaTl,
                                            UpdateTaxaLegalFromBc useCaseTaxaLegal,
                                            UpdateTj6899FromUfirRj updateTj6899FromUfirRj) {
        return new SchedulerConfig(
                useCaseIgpdi,
                useCaseIpcae,
                useCaseIpca15,
                useCaseIgpm,
                useCaseIpca,
                useCaseCdi,
                useCaseSelic,
                useCaseTr,
                useCaseTbf,
                useCasePoupNova,
                useCaseSalario,
                useCasePoupAntiga,
                useCaseIpcaTl,
                useCaseTaxaLegal,
                updateTj6899FromUfirRj);
    }

    @Bean
    public CalculateCdiAccumulatedValueBetweenDates calculateCdiAccumulatedValueBetweenDates(CdiIndexRepository repo) {
        return new CalculateCdiAccumulatedValueBetweenDates(repo);
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
    public CalculateTaxaLegalAccumulatedValueBetweenDates calculateTaxaLegalAccumulatedValueBetweenDates(TaxaLegalIndexRepository repo){
        return new CalculateTaxaLegalAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIpcaTlAccumulatedValueBetweenDates calculateIpcaTlAccumulatedValueBetweenDates(IpcaTlIndexRepository repo){
        return new CalculateIpcaTlAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateTj6899UfirValueBetweenDates calculateTj6899UfirValueBetweenDates(TjL6899IndexRepository repo){
        return new CalculateTj6899UfirValueBetweenDates(repo);
    }

    @Bean
    public CalculatePoupAntigoAccumulatedValueBetweenDates calculatePoupAntigoAccumulatedValueBetweenDates(PoupAntigaIndexRepository repo) {
        return new CalculatePoupAntigoAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateSalarioAccumulatedValueBetweenDates calculateSalarioAccumulatedValueBetweenDates(SalarioIndexRepository repo) {
        return new CalculateSalarioAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculatePoupNovaAccumulatedValueBetweenDates calculatePoupNovaAccumulatedValueBetweenDates(PoupNovaIndexRepository repo) {
        return new CalculatePoupNovaAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIgpdiAccumulatedValueBetweenDates calculateIgpdiAccumulatedValueBetweenDates(IgpdiIndexRepository repo){
        return new CalculateIgpdiAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIpcaeAccumulatedValueBetweenDates calculateIpcaeAccumulatedValueBetweenDates(IpcaeIndexRepository repo){
        return new CalculateIpcaeAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIpca15AccumulatedValueBetweenDates calculateIpca15AccumulatedValueBetweenDates(Ipca15IndexRepository repo){
        return new CalculateIpca15AccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIgpmAccumulatedValueBetweenDates calculateIgpmAccumulatedValueBetweenDates(IgpmIndexRepository repo){
        return new CalculateIgpmAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIpcaAccumulatedValueBetweenDates calculateIpcaAccumulatedValueBetweenDates(IpcaIndexRepository repo){
        return new CalculateIpcaAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateSelicMensalAccumulatedValueBetweenDates calculateSelicAccumulatedValueBetweenDates(SelicMensalIndexRepository repo){
        return new CalculateSelicMensalAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateTrAccumulatedValueBetweenDates calculateTrAccumulatedValueBetweenDates(TrIndexRepository repo){
        return new CalculateTrAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateTbfAccumulatedValueBetweenDates calculateTbfAccumulatedValueBetweenDates(TbfIndexRepository repo){
        return new CalculateTbfAccumulatedValueBetweenDates(repo);
    }

}
