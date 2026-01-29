package application.calculei.infraestructure.repository;

import application.calculei.infraestructure.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIndexRepository extends JpaRepository<BaseEntity, Long> {
}
