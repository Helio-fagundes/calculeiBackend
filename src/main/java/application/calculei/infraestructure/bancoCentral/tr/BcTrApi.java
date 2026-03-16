package application.calculei.infraestructure.bancoCentral.tr;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.tr.port.BuscarTrFromBcPort;
import lombok.RequiredArgsConstructor;
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
public class BcTrApi implements BuscarTrFromBcPort {

    private final RestTemplate restTemplate;
    private final static String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.7811/dados?formato=json";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal) {
        String url = BASE_URL;

        if (dataInicial != null && dataFinal != null) {
            url += "&dataInicial=" + dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "&dataFinal=" + dataFinal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        try {

            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            return List.of(response).stream()
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")),
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
