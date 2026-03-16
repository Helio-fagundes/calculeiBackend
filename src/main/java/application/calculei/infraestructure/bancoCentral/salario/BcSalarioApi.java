package application.calculei.infraestructure.bancoCentral.salario;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.salario.port.BuscarSalarioFromBcPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcSalarioApi implements BuscarSalarioFromBcPort {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.1619/dados?formato=json";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dateInit, LocalDate dateFim) {
        String url = BASE_URL;

        if (dateInit != null && dateFim != null) {
            url += "&dataInicial=" + dateInit.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "&dataFinal=" + dateFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        try{

            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            return List.of(response).stream()
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            d.valor()))
                    .toList();

        }catch (Exception e) {
                throw new RuntimeException("Erro ao buscar dados da API do BC: " + e.getMessage(), e);
        }
    }
}
