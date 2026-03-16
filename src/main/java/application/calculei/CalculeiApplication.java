package application.calculei;

import application.calculei.adapters.gateway.cdi.CdiJpaRepository;
import application.calculei.adapters.gateway.igpdi.IgpdiJpaRepository;
import application.calculei.adapters.gateway.igpm.IgpmJpaRepository;
import application.calculei.adapters.gateway.indice_bc.IndiceBcJpaRepository;
import application.calculei.adapters.gateway.inpc.InpcJpaRepository;
import application.calculei.adapters.gateway.ipc_br.IpcBrJpaRepository;
import application.calculei.adapters.gateway.ipca.IpcaJpaRepository;
import application.calculei.adapters.gateway.ipca_15.Ipca15JpaRepository;
import application.calculei.adapters.gateway.ipca_e.IpcaeJpaRepository;
import application.calculei.adapters.gateway.poupanca_nova.PoupJpaRepository;
import application.calculei.adapters.gateway.salario.SalarioJpaRepository;
import application.calculei.adapters.gateway.selic.SelicDiarioJpaRepository;
import application.calculei.adapters.gateway.selic.SelicMensalJpaRepository;
import application.calculei.adapters.gateway.tbf.TbfJpaRepository;
import application.calculei.adapters.gateway.tr.TrJpaRepository;
import application.calculei.adapters.scheduler.SchedulerConfig;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.inpc.InpcIndexRepository;
import application.calculei.infraestructure.repository.ipc_br.IpcbrIndexRepository;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;
import application.calculei.infraestructure.repository.ipca_15.Ipca15IndexRepository;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicDiarioIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.infraestructure.repository.tbf.TbfIndexRepository;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;
import application.calculei.usecase.cdi.CalculateCdiAccumulatedValueBetweenDates;
import application.calculei.usecase.cdi.UpdateCdiFromBc;
import application.calculei.usecase.cdi.port.BuscarCdiFromBcPort;
import application.calculei.usecase.igpdi.CalculateIgpdiAccumulatedValueBetweenDates;
import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import application.calculei.usecase.igpdi.port.BuscarIgpdiFromBcPort;
import application.calculei.usecase.igpm.CalculateIgpmAccumulatedValueBetweenDates;
import application.calculei.usecase.igpm.UpdateIgpmFromBc;
import application.calculei.usecase.igpm.port.BuscarIgpmFromBcPort;
import application.calculei.usecase.inpc.CalculateInpcAccumulatedValueBetweenDates;
import application.calculei.usecase.inpc.UpdateInpcFromBc;
import application.calculei.usecase.inpc.port.BuscarInpcFromBcPort;
import application.calculei.usecase.ipca.CalculateIpcaAccumulatedValueBetweenDates;
import application.calculei.usecase.ipca.UpdateIpcaFromBc;
import application.calculei.usecase.ipca.port.BuscarIpcaFromBcPort;
import application.calculei.usecase.ipca15.CalculateIpca15AccumulatedValueBetweenDates;
import application.calculei.usecase.ipca15.UpdateIpca15FromBc;
import application.calculei.usecase.ipca15.port.BuscarIpca15FromBcPort;
import application.calculei.usecase.ipcae.CalculateIpcaeAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcae.UpdateIpcaeFromBc;
import application.calculei.usecase.ipcae.port.BuscarIpcaeFromBcPort;
import application.calculei.usecase.ipcbr.CalculateIpcbrAccumulatedValueBetweenDates;
import application.calculei.usecase.ipcbr.UpdateIpcbrFromBc;
import application.calculei.usecase.ipcbr.port.BuscarIpcbrFromBcPort;
import application.calculei.usecase.poupanca_nova.CalculatePoupNovaAccumulatedValueBetweenDates;
import application.calculei.usecase.poupanca_nova.UpdatePoupNovaFromBc;
import application.calculei.usecase.poupanca_nova.port.BuscarPoupNovaFromBcPort;
import application.calculei.usecase.salario.CalculateSalarioAccumulatedValueBetweenDates;
import application.calculei.usecase.salario.UpdateSalarioFromBc;
import application.calculei.usecase.salario.port.BuscarSalarioFromBcPort;
import application.calculei.usecase.selic.diario.CalculateSelicDiarioAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.diario.UpdateSelicDiarioFromBc;
import application.calculei.usecase.selic.diario.port.BuscarSelicDiarioFromBcPort;
import application.calculei.usecase.selic.mensal.CalculateSelicMensalAccumulatedValueBetweenDates;
import application.calculei.usecase.selic.mensal.UpdateSelicMensalFromBc;
import application.calculei.usecase.selic.mensal.port.BuscarSelicMensalFromBcPort;
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
    public IndexRepository indexRepositoryIpca(IpcaIndexRepository repo){
        return new IpcaJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositorySalario(SalarioIndexRepository repo){
        return new SalarioJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositorySelicDiario(SelicDiarioIndexRepository repo){
        return new SelicDiarioJpaRepository(repo);
    }

    @Bean
    public IndexRepository indexRepositoryTr(TrIndexRepository repo){
        return new TrJpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositoryPoupNova(PoupNovaIndexRepository repo){
        return new PoupJpaRepository(repo) {
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
    public IndexRepository indexRepositoryIpcbr(IpcbrIndexRepository repo){
        return new IpcBrJpaRepository(repo) {
        };
    }

    @Bean
    public IndexRepository indexRepositoryInpc(InpcIndexRepository repo){
        return new InpcJpaRepository(repo) {
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
    public UpdateIpcbrFromBc updateIpcbrFromBc(
            BuscarIpcbrFromBcPort buscarIpcbrNoBcPort,
            IpcbrIndexRepository ipcbrIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateIpcbrFromBc(buscarIpcbrNoBcPort, ipcbrIndexRepository, indicesBcIndexRepository);
    }

    @Bean
    public UpdateInpcFromBc updateInpcFromBc(
            BuscarInpcFromBcPort buscarInpcFromBcPort,
            InpcIndexRepository inpcIndexRepository,
            IndicesBcIndexRepository indicesBcIndexRepository) {

        return new UpdateInpcFromBc(buscarInpcFromBcPort, inpcIndexRepository, indicesBcIndexRepository);
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
    public UpdateSelicDiarioFromBc updateSelicDiarioFromBc(
            BuscarSelicDiarioFromBcPort buscarSelicDiarioFromBcPort,
            SelicDiarioIndexRepository repository,
            IndicesBcIndexRepository indicesBcIndexRepository){
        return new UpdateSelicDiarioFromBc(
                buscarSelicDiarioFromBcPort,
                repository,
                indicesBcIndexRepository);
    }


    @Bean
    public SchedulerConfig schedulerConfigd(UpdateIgpdiFromBc useCaseIgpdi,
                                            UpdateIpcaeFromBc useCaseIpcae,
                                            UpdateIpca15FromBc useCaseIpca15,
                                            UpdateIpcbrFromBc useCaseIpcbr,
                                            UpdateInpcFromBc useCaseInpc,
                                            UpdateIgpmFromBc useCaseIgpm,
                                            UpdateIpcaFromBc useCaseIpca,
                                            UpdateCdiFromBc useCaseCdi,
                                            UpdateSelicMensalFromBc useCaseSelic,
                                            UpdateTrFromBc useCaseTr,
                                            UpdateTbfFromBc useCaseTbf,
                                            UpdatePoupNovaFromBc useCasePoupNova,
                                            UpdateSelicDiarioFromBc useCaseSelicDiario,
                                            UpdateSalarioFromBc useCaseSalario) {
        return new SchedulerConfig(
                useCaseIgpdi,
                useCaseIpcae,
                useCaseIpca15,
                useCaseIpcbr,
                useCaseInpc,
                useCaseIgpm,
                useCaseIpca,
                useCaseCdi,
                useCaseSelic,
                useCaseTr,
                useCaseTbf,
                useCasePoupNova,
                useCaseSelicDiario,
                useCaseSalario);
    }

    @Bean
    public CalculateCdiAccumulatedValueBetweenDates calculateCdiAccumulatedValueBetweenDates(CdiIndexRepository repo) {
        return new CalculateCdiAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateSalarioAccumulatedValueBetweenDates calculateSalarioAccumulatedValueBetweenDates(SalarioIndexRepository repo) {
        return new CalculateSalarioAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateSelicDiarioAccumulatedValueBetweenDates calculateSelicDiarioAccumulatedValueBetweenDates(SelicDiarioIndexRepository repo){
        return new CalculateSelicDiarioAccumulatedValueBetweenDates(repo);
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
    public CalculateIpcbrAccumulatedValueBetweenDates calculateIpcbrAccumulatedValueBetweenDates(IpcbrIndexRepository repo){
        return new CalculateIpcbrAccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateIpca15AccumulatedValueBetweenDates calculateIpca15AccumulatedValueBetweenDates(Ipca15IndexRepository repo){
        return new CalculateIpca15AccumulatedValueBetweenDates(repo);
    }

    @Bean
    public CalculateInpcAccumulatedValueBetweenDates calculateInpcAccumulatedValueBetweenDates(InpcIndexRepository repo){
        return new CalculateInpcAccumulatedValueBetweenDates(repo);
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
