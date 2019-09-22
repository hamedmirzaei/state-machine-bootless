package ir.navaco.core.statemachine.repository;

import ir.navaco.core.statemachine.entity.StateMachineFactoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateMachineFactoryRepository extends JpaRepository<StateMachineFactoryEntity, Long> {
    StateMachineFactoryEntity findByFactoryName(String factoryName);
}
