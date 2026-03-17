package application.calculei.infraestructure.bancoCentral.ipca_tl;

import application.calculei.infraestructure.bancoCentral.dto.BcResponse;
import application.calculei.usecase.dto.DadoBancoCentral;
import application.calculei.usecase.ipca_tl.port.BuscarIpcaTlFromBcPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BcIpcaTlApi implements BuscarIpcaTlFromBcPort {

    private final RestTemplate restTemplate;
    private final static String BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.29542/dados?formato=json";

    @Override
    public List<DadoBancoCentral> buscar(LocalDate dataInicio, LocalDate dataFinal) {
        String url = BASE_URL;

        if(dataInicio != null && dataFinal != null){
            url += "&dataInicial=" + dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "&dataFinal="   + dataFinal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        try{

            BcResponse[] response = restTemplate.getForObject(url, BcResponse[].class);

            if (response == null) return List.of();

            return List.of(response).stream()
                    .map(d -> new DadoBancoCentral(LocalDate.parse(d.data(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            d.valor()))
                    .toList();
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar dados da API do BC: " + e.getMessage(), e);
        }
    }
}
