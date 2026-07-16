package application.calculei.infraestructure.banco_central.cdi;

import application.calculei.infraestructure.banco_central.dto.BcResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.domain.port.BuscarCdiFromBcPort;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.domain.port.BuscarUrlBySeriePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BcCdiApi implements BuscarCdiFromBcPort {

    private final RestTemplate restTemplate;
    private final BuscarUrlBySeriePort buscarUrl;
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial, LocalDate dataFinal) {
        String indice = "CDI";
        String url = buscarUrl.buscarUrl(indice);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (dataInicial != null && dataFinal != null) {
            url += "&dataInicial=" + dataInicial.format(dateFormatter)
                    + "&dataFinal="   + dataFinal.format(dateFormatter);
        }

        try {
            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            List<DadoBancoCentral> dadosBancoCentral = Stream.of(response)
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(), dateFormatter), d.valor()))
                    .toList();

            boolean temDadosNovos = dadosBancoCentral.stream()
                    .anyMatch(dado -> dado.data().isAfter(dataInicial));

            if (!temDadosNovos) {
                throw new BancoCentralDataNotFoundException(indice, dataInicial);
            }

            return dadosBancoCentral;
        }
         catch (HttpMessageNotReadableException | RestClientException e) {
            throw new BancoCentralDataNotFoundException(indice, dataInicial);
        }
    }
}
