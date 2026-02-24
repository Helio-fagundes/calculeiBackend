package application.calculei.infraestructure.repository.salario;

import application.calculei.infraestructure.entity.Salario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SalarioIndexRepository extends JpaRepository<Salario, Long> {
    List<Salario> findByValor(Double valor);
    List<Salario> findByDataInitBetween(LocalDate inicio, LocalDate fim);
    List<Salario> findByDataInitLessThanEqual(LocalDate dataInit);
}
