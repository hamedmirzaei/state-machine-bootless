/*
package ir.navaco.core.statemachine.config;

import ir.navaco.core.statemachine.entity.statemachine.Event;
import ir.navaco.core.statemachine.entity.statemachine.StateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.transition.TransitionEntity;

import java.util.Optional;

@Slf4j
@Configuration
@EnableStateMachineFactory(name = SecondStateMachineConfig.SMFB)
public class SecondStateMachineConfig extends EnumStateMachineConfigurerAdapter<StateEntity, Event> {

    public static final String SMFB = "secondStateMachineFactory";

    @Autowired
    StateMachineRuntimePersister<StateEntity, Event, String> jpaRuntimePersist;

    @Override
    public void configure(StateMachineConfigurationConfigurer<StateEntity, Event> config) throws Exception {
        config.withConfiguration()
                .listener(listener())
                .autoStartup(true);
        config.withPersistence()
                .runtimePersister(jpaRuntimePersist);
    }

    private StateMachineListener<StateEntity, Event> listener() {

        return new StateMachineListenerAdapter<StateEntity, Event>() {
            @Override
            public void eventNotAccepted(Message<Event> event) {
                log.error("Not accepted event: {}", event);
            }

            @Override
            public void transition(TransitionEntity<StateEntity, Event> transition) {
                log.warn("MOVE from: {}, to: {}",
                        ofNullableState(transition.getSource()),
                        ofNullableState(transition.getTarget()));
            }

            private Object ofNullableState(org.springframework.statemachine.state.StateEntity s) {
                return Optional.ofNullable(s)
                        .map(org.springframework.statemachine.state.StateEntity::getId)
                        .orElse(null);
            }
        };
    }

    @Override
    public void configure(StateMachineStateConfigurer<StateEntity, Event> states) throws Exception {
        states.withStates()
                .initial(StateEntity.BACKLOG)
                .state(StateEntity.IN_PROGRESS)
                .state(StateEntity.DONE)
                .end(StateEntity.DONE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StateEntity, Event> transitions) throws Exception {

        transitions.withExternal()
                .source(StateEntity.BACKLOG)
                .target(StateEntity.IN_PROGRESS)
                .event(Event.START_FEATURE)
                .and()
                // DEVELOPERS:
                .withExternal()
                .source(StateEntity.IN_PROGRESS)
                .target(StateEntity.DONE)
                .event(Event.FINISH_FEATURE);
    }

}
*/
