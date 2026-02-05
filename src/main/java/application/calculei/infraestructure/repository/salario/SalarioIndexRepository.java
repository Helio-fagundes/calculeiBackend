package application.calculei.infraestructure.repository.salario;

import application.calculei.infraestructure.entity.Salario;

import java.util.Date;
import java.util.List;

public interface SalarioIndexRepository {
    List<Salario> findByValor(Double valor);
    List<Salario> findByDataInitBetween(Date inicio, Date fim);
    List<Salario> findByDataMax(Date data);
}
