package ir.navaco.core.statemachine.repository;

import org.springframework.statemachine.data.StateMachineRepository;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachine;
import org.springframework.stereotype.Repository;

@Repository
public interface MyJpaStateMachineRepository extends StateMachineRepository<JpaRepositoryStateMachine> {
}
