package application.calculei.infraestructure.repository.tbf;

import application.calculei.infraestructure.entity.TBF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TbfIndexRepository extends JpaRepository<TBF, Long> {
    List<TBF> findByValor(Double valor);
    List<TBF> findByDataInitBetween(Date inicio, Date fim);
    List<TBF> findByDataInitLessThanEqual(Date dataInit);
}
