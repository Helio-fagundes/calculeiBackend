package application.calculei.adapters.scheduler;

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
    private final UpdateTj6899FromUfirRj  updateTj6899FromUfirRj;

    @Scheduled(cron = "0 */3 * * * *")
    public void atualizarMensais(){
        igpmusecase.execute();
        poupAntigausecase.execute();
        selicmensalusecase.execute();
        taxaLegalusecase.execute();
        updateTj6899FromUfirRj.execute();
        ipcaeusecase.execute();
        salariousecase.execute();
        poupNovausecase.execute();
        trusecase.execute();
        cdiusecase.execute();
        ipcausecase.execute();
        igpdiusecase.execute();
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void atualizarDiarios(){

    }
}
