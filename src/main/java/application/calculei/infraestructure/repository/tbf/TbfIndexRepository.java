package application.calculei.infraestructure.repository.tbf;

import application.calculei.infraestructure.entity.TBF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TbfIndexRepository extends JpaRepository<TBF, Long> {
    List<TBF> findByValor(Double valor);
    List<TBF> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<TBF> findByDataInitLessThanEqual(LocalDate dataInit);
}
