package application.calculei.usecase.tj_6899;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpdateTj6899FromUfirRj {

    private final IndexRepository tjRepository;
    private final IndexRepository ufirRepository;

    public UpdateTj6899FromUfirRj(IndexRepository tjRepository, IndexRepository ufirRepository) {
        this.tjRepository = tjRepository;
        this.ufirRepository = ufirRepository;
    }

    public void execute() {
        LocalDate ultimaDataUfir = fetchLastDate(ufirRepository);
        LocalDate ultimaDataTj = fetchLastDate(tjRepository);

        if (ultimaDataUfir.getYear() <= ultimaDataTj.getYear()) return;

        BigDecimal fatorTransicao = calculateTransitionFactor(ultimaDataUfir.getYear());

        updatePreviousYearFactors(ultimaDataUfir.getYear() - 1, fatorTransicao);
        createNewYearEntries(ultimaDataUfir.getYear());
    }

    private LocalDate fetchLastDate(IndexRepository repo) {
        return repo.findByLastUpdate()
                .map(Index::getDataInit)
                .orElseThrow(() -> new DataNotFoundException("Não foi possível encontrar datas base."));
    }

    private BigDecimal calculateTransitionFactor(int currentYear) {
        Index ufirAtual = fetchUfirOfYear(currentYear);
        Index ufirAnterior = fetchUfirOfYear(currentYear - 1);

        return ufirAtual.getFator().divide(ufirAnterior.getFator(), 8, RoundingMode.HALF_UP);
    }

    private Index fetchUfirOfYear(int year) {
        LocalDate dataBusca = LocalDate.of(year, 1, 1);
        Index ufir = ufirRepository.findDataInit(dataBusca);

        if (ufir == null) {
            throw new DataNotFoundException("UFIR não encontrada para o ano: " + year);
        }
        return ufir;
    }

    private void updatePreviousYearFactors(int year, BigDecimal factor) {
        List<Index> indicesToUpdate = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            LocalDate data = LocalDate.of(year, mes, 1);
            Index index = tjRepository.findDataInit(data); // Retorna Index ou null

            if (index != null && index.getFator().compareTo(BigDecimal.ONE) == 0) {
                index.setFator(factor);
                indicesToUpdate.add(index);
            }
        }

        if (!indicesToUpdate.isEmpty()) {
            tjRepository.saveAll(indicesToUpdate);
        }
    }

    private void createNewYearEntries(int year) {
        List<Index> newEntries = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            LocalDate dataBusca = LocalDate.of(year, mes, 1);

            if (tjRepository.findDataInit(dataBusca) == null) {

                Index novoIndice = new Index();
                novoIndice.setDataInit(dataBusca);
                novoIndice.setFator(BigDecimal.ONE);

                newEntries.add(novoIndice);
            }
        }

        if (!newEntries.isEmpty()) {
            tjRepository.saveAll(newEntries);
        }
    }
}
