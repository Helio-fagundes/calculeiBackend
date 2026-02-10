package application.calculei.infraestructure.repository.imab;


import application.calculei.infraestructure.entity.IMAB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ImabIndexRepository extends JpaRepository<IMAB, Long> {
    List<IMAB> findByValor(Double valor);
    List<IMAB> findByDataInitBetween(Date inicio, Date fim);
    List<IMAB> findByDataInitLessThanEqual(Date dataInit);
}
