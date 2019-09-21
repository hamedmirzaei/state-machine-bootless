package ir.navaco.core.statemachine.repository;

import ir.navaco.core.statemachine.entity.MyStateMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateMachineRepository extends JpaRepository<MyStateMachine, Long> {
    MyStateMachine findByUuid(String uuid);
}
