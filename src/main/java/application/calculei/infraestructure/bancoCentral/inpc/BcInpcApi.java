package application.calculei.infraestructure.bancoCentral.inpc;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.inpc.port.BuscarInpcFromBcPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcInpcApi implements BuscarInpcFromBcPort {

    private final RestTemplate restTemplate;
    private final static String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.188/dados?formato=json";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial) {
        String url = BASE_URL;
        if (dataInicial != null){
            url += "dataInicial="+ dataInicial.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        BcResponse[] response =
                restTemplate.getForObject(url, BcResponse[].class);

        if (response == null) return List.of();

        return List.of(response).stream()
                .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        d.valor()))
                .toList();
    }
}
