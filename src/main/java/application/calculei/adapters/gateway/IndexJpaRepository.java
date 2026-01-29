package application.calculei.adapters.gateway;

import application.calculei.domain.repository.IndexRepository;
import application.calculei.infraestructure.repository.JpaIndexRepository;

public class IndexJpaRepository implements IndexRepository {

    private final JpaIndexRepository jpaRepository;

    public IndexJpaRepository(JpaIndexRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }


}
