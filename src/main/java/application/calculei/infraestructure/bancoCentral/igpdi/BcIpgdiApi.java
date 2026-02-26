package application.calculei.infraestructure.bancoCentral.igpdi;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.igpdi.dto.DadoIgpdi;
import application.calculei.usecase.igpdi.port.BuscarIgpdiNoBcPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcIpgdiApi implements BuscarIgpdiNoBcPort {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.190/dados?formato=json";

    @Override
    public List<DadoIgpdi> buscar(LocalDate dataInicial) {
        String url = BASE_URL;
        if (dataInicial != null){
            url += "dataInicial="+ dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        ResponseEntity<BcResponse[]> response =
                restTemplate.getForEntity(url, BcResponse[].class);

        BcResponse[] body = response.getBody();

        if (body == null) return List.of();

        return Arrays.stream(body)
                .map(d -> new DadoIgpdi(LocalDate.parse(d.data(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                d.valor()))
                .toList();
    }
}
