package application.calculei.adapters.scheduler;

import application.calculei.usecase.igpdi.UpdateIgpdiFromBc;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IgpdiScheduler {

    private final UpdateIgpdiFromBc usecase;

    public IgpdiScheduler(UpdateIgpdiFromBc usecase) {
        this.usecase = usecase;
    }

    @Scheduled(cron = "0 * * * * *")
    public void atualizarIgpdi(){
        System.out.println("atualizou o igpdi");
        usecase.execute();
    }
}
