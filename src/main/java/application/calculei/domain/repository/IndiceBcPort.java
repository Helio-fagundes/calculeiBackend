package application.calculei.domain.repository;

import application.calculei.infraestructure.entity.IndiceBC;

import java.util.Optional;

public interface IndiceBcPort {
    Optional<IndiceBC> findBySerie(String indiceBC);
}
