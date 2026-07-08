package application.calculei.domain.repository;

import application.calculei.domain.models.IndiceBcDomain;

import java.time.LocalDate;
import java.util.Optional;

public interface IndiceBcPort {
    Optional<IndiceBcDomain> findBySerie(String serie);
    void updateLastUpdate(String serie, LocalDate lastUpdate);
}
