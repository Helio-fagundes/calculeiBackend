package application.calculei.adapters.scheduler;

import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.selic.diario.UpdateSelicDiarioFromBc;
import application.calculei.usecase.tj_6899.UpdateTj6899FromUfirRj;
import application.calculei.usecase.cdi.UpdateCdiFromBc;
import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import application.calculei.usecase.igpm.UpdateIgpmFromBc;
import application.calculei.usecase.ipca.UpdateIpcaFromBc;
import application.calculei.usecase.ipcae.UpdateIpcaeFromBc;
import application.calculei.usecase.poupanca_antiga.UpdatePoupAntigoFromBc;
import application.calculei.usecase.poupanca_nova.UpdatePoupNovaFromBc;
import application.calculei.usecase.salario.UpdateSalarioFromBc;
import application.calculei.usecase.selic.mensal.UpdateSelicMensalFromBc;
import application.calculei.usecase.taxa_legal.UpdateTaxaLegalFromBc;
import application.calculei.usecase.tr.UpdateTrFromBc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

    private final UpdateIgpdiFromBc igpdiusecase;
    private final UpdateIpcaeFromBc ipcaeusecase;
    private final UpdateIgpmFromBc igpmusecase;
    private final UpdateIpcaFromBc ipcausecase;
    private final UpdateCdiFromBc cdiusecase;
    private final UpdateSelicMensalFromBc selicmensalusecase;
    private final UpdateTrFromBc trusecase;
    private final UpdatePoupNovaFromBc poupNovausecase;
    private final UpdateSalarioFromBc salariousecase;
    private final UpdatePoupAntigoFromBc poupAntigausecase;
    private final UpdateTaxaLegalFromBc taxaLegalusecase;
    private final UpdateTj6899FromUfirRj updateTj6899FromUfirRj;
    private final UpdateSelicDiarioFromBc updateSelicDiario;

    @Scheduled(cron = "0 */3 * * * *")
    public void atualizarMensais() {
        log.info("Iniciando atualização dos índices mensais...");

        executeWithSecurity(igpmusecase::execute, "IGP-M");
        executeWithSecurity(selicmensalusecase::execute, "Selic Mensal");
        executeWithSecurity(taxaLegalusecase::execute, "Taxa Legal");
        executeWithSecurity(updateTj6899FromUfirRj::execute, "TJ-6899 / UFIR-RJ");
        executeWithSecurity(ipcaeusecase::execute, "IPCA-E");
        executeWithSecurity(salariousecase::execute, "Salário Mínimo");
        executeWithSecurity(poupNovausecase::execute, "Poupança Nova");
        executeWithSecurity(ipcausecase::execute, "IPCA");
        executeWithSecurity(igpdiusecase::execute, "IGP-DI");

        log.info("Varredura dos índices mensais concluída!");
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void atualizarDiarios() {
        log.info("Iniciando atualização dos índices diários...");

        executeWithSecurity(updateSelicDiario::execute, "Selic Diário");
        executeWithSecurity(cdiusecase::execute, "CDI");
        executeWithSecurity(poupAntigausecase::execute, "Poupança Antiga");
        executeWithSecurity(trusecase::execute, "TR");

        log.info("Varredura dos índices diários concluída!");
    }

    private void executeWithSecurity(Runnable useCaseExecution, String nomeIndice) {
        try {
            useCaseExecution.run();
        } catch (BancoCentralDataNotFoundException e) {
            log.warn(e.getMessage());
        } catch (Exception e) {
            log.error("Erro crítico inesperado ao atualizar o índice (" + nomeIndice + ") :", e);
        }
    }
}
