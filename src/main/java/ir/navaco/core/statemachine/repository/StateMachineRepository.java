package ir.navaco.core.statemachine.repository;

import ir.navaco.core.statemachine.entity.StateMachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateMachineRepository extends JpaRepository<StateMachineEntity, Long> {
    StateMachineEntity findByUuid(String uuid);
}
