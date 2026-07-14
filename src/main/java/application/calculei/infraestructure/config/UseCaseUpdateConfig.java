package application.calculei.infraestructure.config;

import application.calculei.domain.port.*;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.usecase.cdi.UpdateCdiFromBc;
import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import application.calculei.usecase.igpm.UpdateIgpmFromBc;
import application.calculei.usecase.ipca.UpdateIpcaFromBc;
import application.calculei.usecase.ipcae.UpdateIpcaeFromBc;
import application.calculei.usecase.poupanca_antiga.UpdatePoupAntigoFromBc;
import application.calculei.usecase.poupanca_nova.UpdatePoupNovaFromBc;
import application.calculei.usecase.salario.UpdateSalarioFromBc;
import application.calculei.usecase.selic.diario.UpdateSelicDiarioFromBc;
import application.calculei.usecase.selic.mensal.UpdateSelicMensalFromBc;
import application.calculei.usecase.taxa_legal.UpdateTaxaLegalFromBc;
import application.calculei.usecase.tj_6899.UpdateTj6899FromUfirRj;
import application.calculei.usecase.tr.UpdateTrFromBc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseUpdateConfig {

    @Bean
    public UpdateIgpdiFromBc updateIgpdiFromBc(
            BuscarIgpdiFromBcPort indexRepositoryIgpdi,
            @Qualifier("indexRepositoryIgpdi") IndexRepository indexRepository,
            IndiceBcPort indiceBcPort) {

        return new UpdateIgpdiFromBc(indexRepositoryIgpdi, indexRepository, indiceBcPort);
    }

    @Bean
    public UpdateTaxaLegalFromBc updateTaxaLegalFromBc(
            BuscarTaxaLegalFromBcPort buscarTaxaLegalFromBcPort,
            @Qualifier("indexRepositoryTaxaLegal") IndexRepository repository,
            IndiceBcPort indiceBcPort) {
        return new UpdateTaxaLegalFromBc(
                buscarTaxaLegalFromBcPort,
                repository,
                indiceBcPort);
    }

    @Bean
    public UpdateSalarioFromBc updateSalarioFromBc(
            BuscarSalarioFromBcPort buscarSalarioFromBcPort,
            @Qualifier("indexRepositorySalario") IndexRepository indexRepository,
            IndiceBcPort indiceBcPort) {

        return new UpdateSalarioFromBc(buscarSalarioFromBcPort, indexRepository,  indiceBcPort);
    }

    @Bean
    public UpdatePoupNovaFromBc updatePoupNovaFromBc(
            @Qualifier("indexRepositoryPoupNova") IndexRepository repository,
            BuscarPoupNovaFromBcPort buscarPoupNovaFromBcPort,
            IndiceBcPort indiceBcPort) {

        return new UpdatePoupNovaFromBc(repository, buscarPoupNovaFromBcPort, indiceBcPort);
    }

    @Bean
    public UpdateIpcaFromBc updateIpcaFromBc(
            BuscarIpcaFromBcPort buscarIpcaFromBcPort,
            @Qualifier("indexRepositoryIpca") IndexRepository indexRepository,
            IndiceBcPort indiceBcPort) {

        return new UpdateIpcaFromBc(buscarIpcaFromBcPort, indexRepository, indiceBcPort);
    }

    @Bean
    public UpdateIpcaeFromBc updateIpcaeFromBc(
            @Qualifier("indexRepositoryIpcae") IndexRepository indexRepository,
            BuscarIpcaeFromBcPort buscarIpcaeFromBcPort,
            IndiceBcPort indiceBcPort) {

        return new UpdateIpcaeFromBc(indexRepository, buscarIpcaeFromBcPort, indiceBcPort);
    }

    @Bean
    public UpdateIgpmFromBc updateIgpmFromBc(
            BuscarIgpmFromBcPort buscarIgpmFromBcPort,
            @Qualifier("indexRepositoryIgpm") IndexRepository indexRepository,
            IndiceBcPort indiceBcPort) {

        return new UpdateIgpmFromBc(buscarIgpmFromBcPort, indexRepository, indiceBcPort);
    }

    @Bean
    public UpdateCdiFromBc updateCdiFromBc(
            BuscarCdiFromBcPort buscarCdiFromBcPort,
            @Qualifier("indexRepositoryCdi") IndexRepository indexRepository,
            IndiceBcPort indiceBC
    ) {

        return new UpdateCdiFromBc(buscarCdiFromBcPort, indexRepository,  indiceBC);
    }

    @Bean
    public UpdateSelicMensalFromBc updateSelicFromBc(
            BuscarSelicMensalFromBcPort buscarSelicMensalFromBcPort,
            @Qualifier("indexRepositorySelic") IndexRepository indexRepository,
            IndiceBcPort indiceBcPort) {

        return new UpdateSelicMensalFromBc(buscarSelicMensalFromBcPort, indexRepository, indiceBcPort);
    }

    @Bean
    public UpdateTrFromBc updateTrFromBc(
            BuscarTrFromBcPort buscarTrFromBcPort,
            @Qualifier("indexRepositoryTr") IndexRepository repository,
            IndiceBcPort indiceBcPort) {
        return new UpdateTrFromBc(
                buscarTrFromBcPort,
                repository,
                indiceBcPort);
    }

    @Bean
    public UpdatePoupAntigoFromBc updatePoupAntigoFromBc(
            BuscarPoupAntigoFromBcPort buscarPoupAntigoFromBcPort,
            @Qualifier("indexRepositoryPoupAntiga") IndexRepository repository,
            IndiceBcPort indiceBcPort) {
        return new UpdatePoupAntigoFromBc(
                buscarPoupAntigoFromBcPort,
                repository,
                indiceBcPort);
    }

    @Bean
    public UpdateTj6899FromUfirRj updateTj6899FromUfirRj(
            @Qualifier("indexRepositoryTj6899") IndexRepository tjRepository,
            @Qualifier("indexRepositoryTj6899") IndexRepository ufirRjRepository,
            IndiceBcPort indiceBcPort) {
        return new UpdateTj6899FromUfirRj(
                tjRepository,
                ufirRjRepository,
                indiceBcPort
        );
    }

    @Bean
    public UpdateSelicDiarioFromBc updateSelicDiarioFromBc(
            BuscarSelicDiarioFromBcPort  buscarSelicDiarioFromBcPort,
            @Qualifier("indexRepositorySelicDiario") IndexRepository repository,
            IndiceBcPort indiceBcPort
    ){
        return new UpdateSelicDiarioFromBc(buscarSelicDiarioFromBcPort, repository, indiceBcPort);
    }
}
