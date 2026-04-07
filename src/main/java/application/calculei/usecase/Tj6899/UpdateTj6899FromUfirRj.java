package application.calculei.usecase.Tj6899;

import application.calculei.infraestructure.entity.IndiceBC;
import application.calculei.infraestructure.entity.Indice_TJ_L6899;
import application.calculei.infraestructure.entity.UfirRJ;
import application.calculei.infraestructure.repository.indice_tj_L6899.TjL6899IndexRepository;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ufir_rj.UfirRjIndexRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateTj6899FromUfirRj {

    private final UfirRjIndexRepository  ufirRepository;
    private final TjL6899IndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateTj6899FromUfirRj(UfirRjIndexRepository ufirRepository, TjL6899IndexRepository repository,  IndicesBcIndexRepository indicesBcIndexRepository) {
        this.ufirRepository = ufirRepository;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update() {

        LocalDate ultimaUfir = ufirRepository.findMaxDataInit();
        LocalDate ultimaTj = repository.findMaxDataInit();
        int anoUfir = ultimaUfir.getYear();
        int anoTj = ultimaTj.getYear();

        if (anoUfir <= anoTj) {
            return;
        }

        int anoAnterior = anoUfir - 1;

        UfirRJ ufirAtual = ufirRepository.findByDataInit(
                LocalDate.of(anoUfir, 1, 1)
        );

        UfirRJ ufirAnterior = ufirRepository.findByDataInit(
                LocalDate.of(anoAnterior, 1, 1)
        );

        BigDecimal fator = ufirAtual.getFator()
                .divide(ufirAnterior.getFator(), 8, RoundingMode.HALF_UP);

        for (int mes = 1; mes <= 12; mes++) {

            LocalDate dataInit = LocalDate.of(anoAnterior, mes, 1);

            List<Indice_TJ_L6899> lista = repository.findByDataInit(dataInit);

            if (lista.isEmpty()){
                throw new RuntimeException("Nenhuma indice encontrada");
            }

            Indice_TJ_L6899 indice = lista.get(0);

            if (indice.getFator().compareTo(BigDecimal.ONE) == 0) {
                indice.setFator(fator);
                repository.save(indice);
            }
        }

        for (int mes = 1; mes <= 12; mes++) {

            LocalDate novaData = LocalDate.of(anoUfir, mes, 1);

            boolean exists = repository.existsByDataInit(novaData);

            if (!exists) {
                Indice_TJ_L6899 novo = new Indice_TJ_L6899();
                novo.setDataInit(novaData);
                novo.setFator(BigDecimal.ONE);

                IndiceBC indiceBC = indicesBcIndexRepository.findById(19L)
                        .orElseThrow(() -> new RuntimeException("IndiceBC não encontrado"));

                novo.setIndiceBC(indiceBC);

                repository.save(novo);
            }
        }
    }
}
