package ir.navaco.core.statemachine.repository;

import ir.navaco.core.statemachine.entity.MyStateMachineFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateMachineFactoryRepository extends JpaRepository<MyStateMachineFactory, Long> {
    MyStateMachineFactory findByFactoryName(String factoryName);
}
