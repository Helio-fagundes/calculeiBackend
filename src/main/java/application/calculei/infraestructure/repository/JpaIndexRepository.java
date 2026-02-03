package application.calculei.infraestructure.repository;

import application.calculei.infraestructure.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface JpaIndexRepository extends JpaRepository<BaseEntity, Long> {
    List<BaseEntity> findByValor(Double valor);
}
