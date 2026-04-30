package application.calculei.infraestructure.bancoCentral.taxa_legal;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.infraestructure.exceptions.BancoCentralDataNotFoundException;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.domain.port.BuscarUrlBySeriePort;
import application.calculei.domain.port.BuscarTaxaLegalFromBcPort;
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
public class BcTaxaLegalApi implements BuscarTaxaLegalFromBcPort {
    private final RestTemplate restTemplate;
    private final BuscarUrlBySeriePort buscarUrl;
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicio, LocalDate dataFinal) {
        String indice = "TAXA_LEGAL";
        String url = buscarUrl.buscarUrl(indice);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (dataInicio != null && dataFinal != null) {
            url += "&dataInicial=" + dataInicio.format(dateFormatter)
                    + "&dataFinal=" + dataFinal.format(dateFormatter);
        }

        try{
            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            return List.of(response).stream()
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                            dateFormatter),
                            d.valor()))
                    .toList();

        }catch (HttpMessageNotReadableException | RestClientException e){
            throw new BancoCentralDataNotFoundException(indice, dataInicio);
        }
    }
}
