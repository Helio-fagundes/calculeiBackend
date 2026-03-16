package application.calculei.usecase.ipca15;

import application.calculei.infraestructure.entity.IPCA15;
import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.infraestructure.repository.ipca_15.Ipca15IndexRepository;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.ipca15.port.BuscarIpca15FromBcPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class UpdateIpca15FromBc {

    private final BuscarIpca15FromBcPort buscarIpca15FromBcPort;
    private final Ipca15IndexRepository repository;
    private final IndicesBcIndexRepository indicesBcIndexRepository;

    public UpdateIpca15FromBc(BuscarIpca15FromBcPort buscarIpca15FromBcPort, Ipca15IndexRepository repository, IndicesBcIndexRepository indicesBcIndexRepository) {
        this.buscarIpca15FromBcPort = buscarIpca15FromBcPort;
        this.repository = repository;
        this.indicesBcIndexRepository = indicesBcIndexRepository;
    }

    public void update(){

        LocalDate lastDate = repository.findMaxDataInit();
        List<DadoBancoCentral> dados;

        if (lastDate == null){
            dados = buscarIpca15FromBcPort.buscar(null);
        } else {
            dados = buscarIpca15FromBcPort.buscar(lastDate.plusMonths(1));
        }

        var indice = indicesBcIndexRepository.findBySerie("IPCA15")
                .orElseThrow(() -> new RuntimeException("Indice BC não encontrado"));

        for (var dado : dados){

            if (Boolean.TRUE.equals(repository.existsByDataInit(dado.data())))
                continue;

            BigDecimal percentual = new BigDecimal(dado.valor().replace(",", "."));

            BigDecimal fator = percentual
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .add(BigDecimal.ONE);

            IPCA15 ipca15 = new IPCA15();
            ipca15.setDataInit(dado.data());
            ipca15.setFator(fator);
            ipca15.setIndiceBC(indice);

            repository.save(ipca15);
        }

    }
}
