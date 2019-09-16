package ir.navaco.core.statemachine.config;

import ir.navaco.core.statemachine.entity.statemachine.Events;
import ir.navaco.core.statemachine.entity.statemachine.States;
import ir.navaco.core.statemachine.repository.MyJpaStateMachineRepository;
import ir.navaco.core.statemachine.repository.MyJpaStateMachineRepository2;
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

    @Autowired
    private MyJpaStateMachineRepository2 myJpaStateMachineRepository2;

    @Bean
    public StateMachineRuntimePersister<States, Events, String> jpaRuntimePersist() {
        return new JpaPersistingStateMachineInterceptor<>(myJpaStateMachineRepository2);
    }

    @Bean
    public StateMachinePersister<States, Events, String>
    persister(StateMachinePersist<States, Events, String> defaultPersist) {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }
}
