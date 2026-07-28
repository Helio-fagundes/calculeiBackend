package application.calculei.infraestructure.banco_central.igpm;

import application.calculei.infraestructure.banco_central.dto.BcResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.domain.port.BuscarIgpmFromBcPort;
import application.calculei.domain.port.BuscarUrlBySeriePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BcIgpmApi implements BuscarIgpmFromBcPort {

    private final BuscarUrlBySeriePort buscarUrl;
    private final RestTemplate restTemplate;
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial) {
        String indice = "IGPM";
        String url = buscarUrl.buscarUrl(indice);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (dataInicial != null){
            url += "dataInicial="+ dataInicial.format(dateFormatter);
        }

        try {
            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            List<DadoBancoCentral> dadosBancoCentral = Stream.of(response)
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(), dateFormatter), d.valor()))
                    .toList();

            boolean temDadosNovos = dadosBancoCentral.stream()
                    .anyMatch(dado -> !dado.data().isBefore(dataInicial));

            if (!temDadosNovos) {
                log.info("[Banco Central] Índice '{}' já está atualizado. Nenhum dado novo a partir de {}.", indice, dataInicial);
                return List.of();
            }

            return dadosBancoCentral.stream()
                    .filter(dado -> !dado.data().isBefore(dataInicial))
                    .toList();

        }catch (HttpMessageNotReadableException | RestClientException e){
            throw new BancoCentralDataNotFoundException(indice, dataInicial);
        }
    }
}
