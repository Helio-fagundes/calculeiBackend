package application.calculei.usecase.port;

import application.calculei.infraestructure.entity.IndiceBC;

import java.util.Optional;

public interface BuscarUrlBySeriePort {
    String  buscarUrl(String serie);
}
