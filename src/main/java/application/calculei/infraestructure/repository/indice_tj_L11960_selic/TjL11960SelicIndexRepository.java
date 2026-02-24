package application.calculei.infraestructure.repository.indice_tj_L11960_selic;

import application.calculei.infraestructure.entity.Indice_TJ_L11960_Selic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TjL11960SelicIndexRepository extends JpaRepository<Indice_TJ_L11960_Selic, Long> {
    List<Indice_TJ_L11960_Selic> findByValor(Double valor);
    List<Indice_TJ_L11960_Selic> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<Indice_TJ_L11960_Selic> findByDataInitLessThanEqual(LocalDate dataInit);
}
