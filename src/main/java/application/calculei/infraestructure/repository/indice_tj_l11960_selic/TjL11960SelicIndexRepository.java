package application.calculei.infraestructure.repository.indice_tj_l11960_selic;

import application.calculei.infraestructure.entity.IndiceTjl11960Selic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TjL11960SelicIndexRepository extends JpaRepository<IndiceTjl11960Selic, Long> {
    List<IndiceTjl11960Selic> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<IndiceTjl11960Selic> findByDataInitLessThanEqual(LocalDate dataInit);
    IndiceTjl11960Selic findByDataInit(LocalDate dataInit);

}
