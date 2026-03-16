package application.calculei.infraestructure.bancoCentral.cdi;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.cdi.port.BuscarCdiFromBcPort;
import application.calculei.usecase.dto.DadoBancoCentral;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcCdiApi implements BuscarCdiFromBcPort {

    private final RestTemplate restTemplate;
    private final static String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.12/dados?formato=json";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal) {
        String url = BASE_URL;

        if (dataInicial != null && dataFinal != null) {
            url += "&dataInicial=" + dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "&dataFinal="   + dataFinal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        try {
            ResponseEntity<BcResponse[]> response = restTemplate.getForEntity(url, BcResponse[].class);
            BcResponse[] body = response.getBody();

            if (body == null) return List.of();

            return List.of(body).stream()
                    .map(d -> new DadoBancoCentral(
                            LocalDate.parse(d.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            d.valor()))
                    .toList();

        } catch (HttpClientErrorException.NotFound e) {
            return List.of();
        } catch (RestClientException e) {
            if (e.getCause() instanceof HttpMessageNotReadableException) {
                return List.of();
            }
            throw new RuntimeException("Erro ao buscar dados da API do BC: " + e.getMessage(), e);
        }
    }
}
