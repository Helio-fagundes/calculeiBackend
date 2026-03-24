package application.calculei.infraestructure.bancoCentral.igpdi;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.igpdi.port.BuscarIgpdiFromBcPort;
import application.calculei.usecase.port.BuscarUrlBySeriePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcIpgdiApi implements BuscarIgpdiFromBcPort {

    private final RestTemplate restTemplate;
    private static BuscarUrlBySeriePort buscarUrl;

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicial) {
        String indice = "IGPDI";
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
