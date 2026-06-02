package application.calculei.infraestructure.bancoCentral.igpdi;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.domain.port.BuscarIgpdiFromBcPort;
import application.calculei.domain.port.BuscarUrlBySeriePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcIpgdiApi implements BuscarIgpdiFromBcPort {

    private final RestTemplate restTemplate;
    private final BuscarUrlBySeriePort buscarUrl;
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial) {
        String indice = "IGPDI";
        String url = buscarUrl.buscarUrl(indice);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (dataInicial != null){
            url += "dataInicial="+ dataInicial.format(dateFormatter);
        }

        try {
            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            List<DadoBancoCentral> dadosBancoCentral = List.of(response).stream()
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(), dateFormatter), d.valor()))
                    .toList();

            boolean temDadosNovos = dadosBancoCentral.stream()
                    .anyMatch(dado -> dado.data().isAfter(dataInicial));

            if (!temDadosNovos) {
                throw new BancoCentralDataNotFoundException(indice, dataInicial);
            }

            return dadosBancoCentral;

        }catch (HttpMessageNotReadableException | RestClientException e){
            throw new BancoCentralDataNotFoundException(indice, dataInicial);
        }
    }
}
