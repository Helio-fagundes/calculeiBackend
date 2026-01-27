package application.calculei.domain.repository.interfaces;

import java.util.List;

public interface IndicesInterface<T> {

    List<T> findByValor(Double valor);

    List<T> findByLastUpdated();
}
