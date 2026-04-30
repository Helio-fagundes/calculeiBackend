package application.calculei.infraestructure.adapters;

import application.calculei.infraestructure.repository.indices_bc.IndicesBcIndexRepository;
import application.calculei.domain.port.BuscarUrlBySeriePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuscarUrlBySerieAdapters implements BuscarUrlBySeriePort {

    private final IndicesBcIndexRepository repository;

    @Override
    public String buscarUrl(String serie) {
        return repository.findBySerie(serie)
                .orElseThrow(() -> new IllegalArgumentException("Indice não encontrado: " + serie))
                .getUrlBC();
    }
}
