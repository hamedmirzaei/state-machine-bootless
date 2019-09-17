package ir.navaco.core.statemachine.config;

import ir.navaco.core.statemachine.entity.statemachine.Event;
import ir.navaco.core.statemachine.entity.statemachine.State;
import ir.navaco.core.statemachine.repository.MyJpaStateMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class PersistConfig {

    @Autowired
    private MyJpaStateMachineRepository myJpaStateMachineRepository;

    @Bean
    public StateMachineRuntimePersister<State, Event, String> jpaRuntimePersist() {
        return new JpaPersistingStateMachineInterceptor<>(myJpaStateMachineRepository);
    }

    @Bean
    public StateMachinePersister<State, Event, String>
    persister(StateMachinePersist<State, Event, String> defaultPersist) {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }
}
