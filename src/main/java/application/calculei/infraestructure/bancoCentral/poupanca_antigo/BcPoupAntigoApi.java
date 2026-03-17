package application.calculei.infraestructure.bancoCentral.poupanca_antigo;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.poupanca_antiga.port.BuscarPoupAntigoFromBcPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcPoupAntigoApi implements BuscarPoupAntigoFromBcPort {

    private final RestTemplate restTemplate;
    private static String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.25/dados?formato=json";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicio, LocalDate dataFim) {
        String url = BASE_URL;

        if (dataInicio != null && dataFim != null){
            url += "&dataInicial=" + dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "&dataFinal=" + dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        try {

            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            return List.of(response).stream()
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            d.valor()))
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar dados da API do BC: " + e.getMessage(), e);
        }
    }
}
