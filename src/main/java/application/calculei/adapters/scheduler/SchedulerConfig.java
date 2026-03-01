package application.calculei.adapters.scheduler;

import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfig {

    private final UpdateIgpdiFromBc Igpdiusecase;

    public SchedulerConfig(UpdateIgpdiFromBc Igpdiusecase) {
        this.Igpdiusecase = Igpdiusecase;
    }

    @Scheduled(cron = "0 0 3 5 * ?")
    public void atualizarMensais(){
        Igpdiusecase.execute();
    }
}
