package application.calculei.adapters.scheduler;

import application.calculei.usecase.cdi.UpdateCdiFromBc;
import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import application.calculei.usecase.igpm.UpdateIgpmFromBc;
import application.calculei.usecase.inpc.UpdateInpcFromBc;
import application.calculei.usecase.ipca.UpdateIpcaFromBc;
import application.calculei.usecase.ipca15.UpdateIpca15FromBc;
import application.calculei.usecase.ipca_tl.UpdateIpcaTlFromBc;
import application.calculei.usecase.ipcae.UpdateIpcaeFromBc;
import application.calculei.usecase.ipcbr.UpdateIpcbrFromBc;
import application.calculei.usecase.poupanca_antiga.UpdatePoupAntigoFromBc;
import application.calculei.usecase.poupanca_nova.UpdatePoupNovaFromBc;
import application.calculei.usecase.salario.UpdateSalarioFromBc;
import application.calculei.usecase.selic.diario.UpdateSelicDiarioFromBc;
import application.calculei.usecase.selic.mensal.UpdateSelicMensalFromBc;
import application.calculei.usecase.taxa_legal.UpdateTaxaLegalFromBc;
import application.calculei.usecase.tbf.UpdateTbfFromBc;
import application.calculei.usecase.tr.UpdateTrFromBc;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final UpdateIgpdiFromBc Igpdiusecase;
    private final UpdateIpcaeFromBc ipcaeusecase;
    private final UpdateIpca15FromBc ipca15usecase;
    private final UpdateIpcbrFromBc ipcbrusecase;
    private final UpdateInpcFromBc inpcusecase;
    private final UpdateIgpmFromBc igpmusecase;
    private final UpdateIpcaFromBc ipcausecase;
    private final UpdateCdiFromBc cdiusecase;
    private final UpdateSelicMensalFromBc selicmensalusecase;
    private final UpdateTrFromBc trusecase;
    private final UpdateTbfFromBc tbfusecase;
    private final UpdatePoupNovaFromBc poupNovausecase;
    private final UpdateSelicDiarioFromBc selicdiariousecase;
    private final UpdateSalarioFromBc salariousecase;
    private final UpdatePoupAntigoFromBc poupAntigausecase;
    private final UpdateIpcaTlFromBc ipcaTlusecase;
    private final UpdateTaxaLegalFromBc taxaLegalusecase;

    @Scheduled(cron = "0 * * * * *")
    public void atualizarMensais(){
        Igpdiusecase.update();
        ipcaeusecase.update();
        ipca15usecase.update();
        ipcbrusecase.update();
        inpcusecase.update();
        igpmusecase.update();
        ipcausecase.update();
        selicmensalusecase.update();
        salariousecase.update();
        poupNovausecase.update();
        poupAntigausecase.update();
        ipcaTlusecase.update();
        taxaLegalusecase.update();
    }

    @Scheduled(cron = "0 * * * * *")
    public void atualizarDiarios(){
        cdiusecase.update();
        trusecase.update();
        tbfusecase.update();
        selicdiariousecase.update();
    }

}
