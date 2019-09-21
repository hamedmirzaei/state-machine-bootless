/*
package ir.navaco.core.statemachine.config;

import ir.navaco.core.statemachine.entity.statemachine.Event;
import ir.navaco.core.statemachine.entity.statemachine.State;
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
import org.springframework.statemachine.transition.Transition;

import java.util.Optional;

@Slf4j
@Configuration
@EnableStateMachineFactory(name = SecondStateMachineConfig.SMFB)
public class SecondStateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {

    public static final String SMFB = "secondStateMachineFactory";

    @Autowired
    StateMachineRuntimePersister<State, Event, String> jpaRuntimePersist;

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        config.withConfiguration()
                .listener(listener())
                .autoStartup(true);
        config.withPersistence()
                .runtimePersister(jpaRuntimePersist);
    }

    private StateMachineListener<State, Event> listener() {

        return new StateMachineListenerAdapter<State, Event>() {
            @Override
            public void eventNotAccepted(Message<Event> event) {
                log.error("Not accepted event: {}", event);
            }

            @Override
            public void transition(Transition<State, Event> transition) {
                log.warn("MOVE from: {}, to: {}",
                        ofNullableState(transition.getSource()),
                        ofNullableState(transition.getTarget()));
            }

            private Object ofNullableState(org.springframework.statemachine.state.State s) {
                return Optional.ofNullable(s)
                        .map(org.springframework.statemachine.state.State::getId)
                        .orElse(null);
            }
        };
    }

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states.withStates()
                .initial(State.BACKLOG)
                .state(State.IN_PROGRESS)
                .state(State.DONE)
                .end(State.DONE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {

        transitions.withExternal()
                .source(State.BACKLOG)
                .target(State.IN_PROGRESS)
                .event(Event.START_FEATURE)
                .and()
                // DEVELOPERS:
                .withExternal()
                .source(State.IN_PROGRESS)
                .target(State.DONE)
                .event(Event.FINISH_FEATURE);
    }

}
*/
