package application.calculei.infraestructure.repository.igpdi;

import application.calculei.infraestructure.entity.IGPDI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IgpdiIndexRepository extends JpaRepository<IGPDI, Long> {
    List<IGPDI> findByValor(Double valor);
    List<IGPDI> findByDataInitBetween(LocalDate dataInit, LocalDate fim);
    List<IGPDI> findByDataInitLessThanEqual(LocalDate dataInit);
}
