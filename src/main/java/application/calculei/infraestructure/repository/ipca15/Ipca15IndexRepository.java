package application.calculei.infraestructure.repository.ipca15;

import application.calculei.infraestructure.entity.IPCA15;

import java.util.Date;
import java.util.List;

public interface Ipca15IndexRepository {
    List<IPCA15> findByValor(Double valor);
    List<IPCA15> findByDataInitBetween(Date inicio, Date fim);
    List<IPCA15> findByDataMax(Date data);
}
