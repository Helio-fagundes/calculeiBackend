package application.calculei.usecase.ufir;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UfirUseCase {

    private final IndexRepository repository;
    private final IndiceBcPort  indiceBcPort;

    public UfirUseCase(IndexRepository repository, IndiceBcPort indiceBcPort) {
        this.repository = repository;
        this.indiceBcPort = indiceBcPort;
    }

    public void updateLastUfirValue() {
        LocalDate maxSaved = repository.findMaxDataInit();
        indiceBcPort.updateLastUpdate("UFIR", maxSaved);
    }

    public BigDecimal getLastUfirValue(){
        return repository.findByLastUpdate()
                .map(Index::getFator)
                .orElseThrow(() -> new DataNotFoundException("Valor Ufir não encontrado"));
    }
}
