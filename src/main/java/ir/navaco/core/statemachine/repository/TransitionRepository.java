package ir.navaco.core.statemachine.repository;

import ir.navaco.core.statemachine.entity.MyTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionRepository extends JpaRepository<MyTransition, Long> {
}
