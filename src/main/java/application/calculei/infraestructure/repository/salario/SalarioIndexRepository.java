package application.calculei.infraestructure.repository.salario;

import application.calculei.infraestructure.entity.Salario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SalarioIndexRepository extends JpaRepository<Salario, Long> {
    List<Salario> findByValor(Double valor);
    List<Salario> findByDataInitBetween(Date inicio, Date fim);
    List<Salario> findByDataInitLessThanEqual(Date dataInit);
}
