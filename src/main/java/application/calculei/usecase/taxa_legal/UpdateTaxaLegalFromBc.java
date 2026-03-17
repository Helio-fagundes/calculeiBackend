package application.calculei.usecase.taxa_legal;

import application.calculei.infraestructure.entity.IPCA_Tl;
import application.calculei.infraestructure.entity.TaxaLegal;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.taxa_legal.TaxaLegalIndexRepository;
import application.calculei.usecase.taxa_legal.port.BuscarTaxaLegalFromBcPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateTaxaLegalFromBc {

    private final BuscarTaxaLegalFromBcPort buscarTaxaLegalFromBcPort;
    private final TaxaLegalIndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateTaxaLegalFromBc(BuscarTaxaLegalFromBcPort buscarTaxaLegalFromBcPort, TaxaLegalIndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarTaxaLegalFromBcPort = buscarTaxaLegalFromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){
        LocalDate dataMax = repository.findMaxDateInit();
        LocalDate now = LocalDate.now();
        LocalDate inicio = dataMax != null ? dataMax.plusDays(1) : LocalDate.of(2023, 1, 1);

        if (inicio.isAfter(now)) return;

        var indice = indicesBcIndexRepository.findBySerie("IPCA_TL")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        do {
            LocalDate fim = inicio.plusYears(5).minusDays(1);
            if (fim.isAfter(now)) fim = now;

            for (var dado : buscarTaxaLegalFromBcPort.buscar(inicio, fim)) {
                if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                    continue;

                BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

                BigDecimal fator = percentual
                        .divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP)
                        .add(BigDecimal.ONE);

                var taxaLegal = new TaxaLegal();
                taxaLegal.setDataInit(dado.data());
                taxaLegal.setFator(fator);
                taxaLegal.setIndiceBC(indice);

                repository.save(taxaLegal);
            }
            inicio.plusYears(5);
        } while (inicio.isBefore(now));
        }
    }
