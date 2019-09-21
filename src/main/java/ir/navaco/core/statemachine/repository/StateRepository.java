package ir.navaco.core.statemachine.repository;

import ir.navaco.core.statemachine.entity.MyState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<MyState, Long> {
}
