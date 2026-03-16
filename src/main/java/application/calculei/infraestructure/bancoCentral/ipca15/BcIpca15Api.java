package application.calculei.infraestructure.bancoCentral.ipca15;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.ipca15.port.BuscarIpca15FromBcPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcIpca15Api implements BuscarIpca15FromBcPort {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.7478/dados?formato=json";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial) {
        String url = BASE_URL;
        if (dataInicial != null){
            url += "dataInicial="+ dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        BcResponse[] response =
                restTemplate.getForObject(url, BcResponse[].class);

        if (response == null) return List.of();

        return List.of(response).stream()
                .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        d.valor()))
                .toList();

    }
}
