package application.calculei.infraestructure.repository.tr;

import application.calculei.infraestructure.entity.TR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TrIndexRepository extends JpaRepository<TR, Long> {
    List<TR> findByValor(Double valor);
    List<TR> findByDataInitBetween(Date inicio, Date fim);
    List<TR> findByDataInitLessThanEqual(Date dataInit);
}
