package application.calculei.infraestructure.bancoCentral.Igpm;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.igpm.port.BuscarIgpmFromBcPort;
import application.calculei.usecase.port.BuscarUrlBySeriePort;
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
public class BcIgpmApi implements BuscarIgpmFromBcPort {

    private final BuscarUrlBySeriePort buscarUrl;
    private final RestTemplate restTemplate;

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial) {
        String indice = "IGPM";
        String url = buscarUrl.buscarUrl(indice);

        if (dataInicial != null){
            url += "dataInicial="+ dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        try {
            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            return List.of(response)
                    .stream()
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            d.valor()))
                    .toList();

        }catch (HttpMessageNotReadableException | RestClientException e){
            throw new BancoCentralDataNotFoundException(indice, dataInicial);
        }
    }
}
