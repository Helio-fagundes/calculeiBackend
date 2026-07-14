package application.calculei.infraestructure.config;

import application.calculei.adapters.gateway.cdi.CdiJpaRepository;
import application.calculei.adapters.gateway.history_pdf_value.HistoryJpaRepository;
import application.calculei.adapters.gateway.igpdi.IgpdiJpaRepository;
import application.calculei.adapters.gateway.igpm.IgpmJpaRepository;
import application.calculei.adapters.gateway.indice_bc.IndiceBcJpaRepository;
import application.calculei.adapters.gateway.indice_tj_l11960_selic.IndiceTjJpaRepository;
import application.calculei.adapters.gateway.ipca.IpcaJpaRepository;
import application.calculei.adapters.gateway.ipca_e.IpcaeJpaRepository;
import application.calculei.adapters.gateway.poupanca_antiga.PoupAntigaJpaRepository;
import application.calculei.adapters.gateway.poupanca_nova.PoupNovaJpaRepository;
import application.calculei.adapters.gateway.salario.SalarioJpaRepository;
import application.calculei.adapters.gateway.selic.SelicDiarioJpaRepository;
import application.calculei.adapters.gateway.selic.SelicMensalJpaRepository;
import application.calculei.adapters.gateway.taxa_legal.TaxaLegalJpaRepository;
import application.calculei.adapters.gateway.tr.TrJpaRepository;
import application.calculei.adapters.gateway.ufir_rj.UfirRjJpaRepository;
import application.calculei.domain.repository.HistoryPdfValuePort;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.repository.cdi.CdiIndexRepository;
import application.calculei.infraestructure.repository.history_pdf_value.HistoryPdfValueRepository;
import application.calculei.infraestructure.repository.igpdi.IgpdiIndexRepository;
import application.calculei.infraestructure.repository.igpm.IgpmIndexRepository;
import application.calculei.infraestructure.repository.indice_tj_l11960_selic.TjL11960SelicIndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca.IpcaIndexRepository;
import application.calculei.infraestructure.repository.ipca_e.IpcaeIndexRepository;
import application.calculei.infraestructure.repository.poupanca_antiga.PoupAntigaIndexRepository;
import application.calculei.infraestructure.repository.poupanca_nova.PoupNovaIndexRepository;
import application.calculei.infraestructure.repository.salario.SalarioIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicDiarioIndexRepository;
import application.calculei.infraestructure.repository.selic.SelicMensalIndexRepository;
import application.calculei.infraestructure.repository.taxa_legal.TaxaLegalIndexRepository;
import application.calculei.infraestructure.repository.tr.TrIndexRepository;
import application.calculei.infraestructure.repository.ufir_rj.UfirRjIndexRepository;
import application.calculei.usecase.index_monetary_correction.IndexMonetaryCorrection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InfrastructureConfig {

    @Bean
    public IndexRepository indexRepositoryCdi(
            CdiIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new CdiJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryUfir(
            UfirRjIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new UfirRjJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositorySelicDiario(
            SelicDiarioIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new SelicDiarioJpaRepository(repo, indicesBcIndexRepository);
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
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new UfirRjJpaRepository(repo,  indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryTj11960(
            TjL11960SelicIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new IndiceTjJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIpca(
            IpcaIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new IpcaJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositorySalario(
            SalarioIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new SalarioJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryPoupAntiga(
            PoupAntigaIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new PoupAntigaJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryTr(
            TrIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new TrJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryPoupNova(
            PoupNovaIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new PoupNovaJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositorySelic(
            SelicMensalIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new SelicMensalJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIgpdi(
            IgpdiIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new IgpdiJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIpcae(
            IpcaeIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
        return new IpcaeJpaRepository(repo, indicesBcIndexRepository);
    }

    @Bean
    public IndexRepository indexRepositoryIgpm(
            IgpmIndexRepository repo,
            IndicesBcIndexRepository indicesBcIndexRepository) {
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
    public HistoryPdfValuePort historyPdfValuePort(HistoryPdfValueRepository repo) {
        return new HistoryJpaRepository(repo);
    }

    @Bean
    public IndexMonetaryCorrection indexMonetaryCorrection() {
        return new IndexMonetaryCorrection();
    }

    @Bean
    public JavaMailSender  javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
