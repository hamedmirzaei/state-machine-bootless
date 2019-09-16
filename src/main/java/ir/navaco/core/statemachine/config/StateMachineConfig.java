package ir.navaco.core.statemachine.config;

import ir.navaco.core.statemachine.entity.statemachine.Events;
import ir.navaco.core.statemachine.entity.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Optional;

@Slf4j
@Configuration
@EnableStateMachineFactory(name = StateMachineConfig.SMFB)
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    public static final String SMFB = "stateMachineFactory";

    @Autowired
    StateMachineRuntimePersister<States, Events, String> jpaRuntimePersist;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .listener(listener())
                .autoStartup(true);
        config.withPersistence()
                .runtimePersister(jpaRuntimePersist);
    }

    private StateMachineListener<States, Events> listener() {

        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void eventNotAccepted(Message<Events> event) {
                log.error("Not accepted event: {}", event);
            }

            @Override
            public void transition(Transition<States, Events> transition) {
                log.warn("MOVE from: {}, to: {}",
                        ofNullableState(transition.getSource()),
                        ofNullableState(transition.getTarget()));
            }

            private Object ofNullableState(State s) {
                return Optional.ofNullable(s)
                        .map(State::getId)
                        .orElse(null);
            }
        };
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.BACKLOG, developersWakeUpAction())
                .state(States.IN_PROGRESS, weNeedCoffeeAction())
                .state(States.TESTING, qaWakeUpAction())
                .state(States.DONE, goToSleepAction())
                .end(States.DONE);
    }

    @Bean
    public Action<States, Events> developersWakeUpAction() {
        return stateContext -> log.warn("Forget about it!");
    }

    @Bean
    public Action<States, Events> weNeedCoffeeAction() {
        return stateContext -> log.warn("No coffee!");
    }

    @Bean
    public Action<States, Events> qaWakeUpAction() {
        return stateContext -> log.warn("Wake up a testing team, the sun is high!");
    }

    @Bean
    public Action<States, Events> goToSleepAction() {
        return stateContext -> log.warn("All sleep! customer is satisfied.");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {

        transitions.withExternal()
                .source(States.BACKLOG)
                .target(States.IN_PROGRESS)
                .event(Events.START_FEATURE)
                .and()
                // DEVELOPERS:
                .withExternal()
                .source(States.IN_PROGRESS)
                .target(States.TESTING)
                .event(Events.FINISH_FEATURE)
                .guard(alreadyDeployedGuard())
                .and()
                // QA-TEAM:
                .withExternal()
                .source(States.TESTING)
                .target(States.DONE)
                .event(Events.QA_CHECKED_UC)
                .and()
                .withExternal()
                .source(States.TESTING)
                .target(States.IN_PROGRESS)
                .event(Events.QA_REJECTED_UC)
                .and()
                // ROCK-STAR:
                .withExternal()
                .source(States.BACKLOG)
                .target(States.TESTING)
                .event(Events.ROCK_STAR_DOUBLE_TASK)
                .and()
                // DEVOPS:
                .withInternal()
                .source(States.IN_PROGRESS)
                .event(Events.DEPLOY)
                .action(deployPreProd())
                .and()
                .withInternal()
                .source(States.BACKLOG)
                .event(Events.DEPLOY)
                .action(deployPreProd());
    }

    @Bean
    public Guard<States, Events> alreadyDeployedGuard() {
        return context -> Optional.ofNullable(context.getExtendedState().getVariables().get("deployed"))
                .map(v -> (boolean) v)
                .orElse(false);
    }

    @Bean
    public Action<States, Events> deployPreProd() {
        return stateContext -> {
            log.warn("DEPLOY: We are rolling out on pre-production.");
            stateContext.getExtendedState().getVariables().put("deployed", true);
        };
    }
}
