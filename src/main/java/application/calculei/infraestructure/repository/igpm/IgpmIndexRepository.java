package application.calculei.infraestructure.repository.igpm;


import application.calculei.infraestructure.entity.IGPM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IgpmIndexRepository extends JpaRepository<IGPM, Long> {
    List<IGPM> findByValor(Double valor);
    List<IGPM> findByDataInitBetween(Date dataInit, Date fim);
    List<IGPM> findByDataInitLessThanEqual(Date dataInit);
}
