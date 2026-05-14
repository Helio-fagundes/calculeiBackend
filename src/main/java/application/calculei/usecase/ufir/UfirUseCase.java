package application.calculei.usecase.ufir;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.math.BigDecimal;

public class UfirUseCase {

    private final IndexRepository repository;

    public UfirUseCase(IndexRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getLastUfirValue(){
        return repository.findByLastUpdate()
                .map(Index::getFator)
                .orElseThrow(() -> new DataNotFoundException("UFIR value not found"));
    }
}
