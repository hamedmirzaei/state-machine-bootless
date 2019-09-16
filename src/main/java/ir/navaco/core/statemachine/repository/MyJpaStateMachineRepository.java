package ir.navaco.core.statemachine.repository;

import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyJpaStateMachineRepository extends JpaStateMachineRepository {
}
