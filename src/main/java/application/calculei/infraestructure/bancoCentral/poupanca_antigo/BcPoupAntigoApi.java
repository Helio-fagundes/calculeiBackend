package application.calculei.infraestructure.bancoCentral.poupanca_antigo;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.port.BuscarUrlBySeriePort;
import application.calculei.usecase.poupanca_antiga.port.BuscarPoupAntigoFromBcPort;
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
public class BcPoupAntigoApi implements BuscarPoupAntigoFromBcPort {

    private final RestTemplate restTemplate;
    private final BuscarUrlBySeriePort buscarUrl;

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicio, LocalDate dataFim) {
        String indice = "POUPANTIGA";
        String url = buscarUrl.buscarUrl(indice);

        if (dataInicio != null && dataFim != null){
            url += "&dataInicial=" + dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "&dataFinal=" + dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
            throw new BancoCentralDataNotFoundException(indice, dataInicio);
        }
    }
}
