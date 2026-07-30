package application.calculei.usecase.ufir;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.ufir.dto.UfirValueRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

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

    public void saveUfirValue(UfirValueRequestDto request) {
        List<Index> ufirYear = IntStream.rangeClosed(1, 12)
                .mapToObj(month -> new Index(null, request.valueUfir(), LocalDate.of(request.year(), month, 1)))
                .toList();

        repository.saveAll(ufirYear);
        indiceBcPort.updateLastUpdate("UFIR", LocalDate.of(request.year(), 12, 1));
    }

    public BigDecimal getLastUfirValue(){
        return repository.findByLastUpdate()
                .map(Index::getFator)
                .orElseThrow(() -> new DataNotFoundException("Valor Ufir não encontrado"));
    }
}
