/*
package ir.navaco.core.statemachine.entity.statemachine.resolver;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachineEntity;
import org.springframework.statemachine.support.DefaultStateContext;
import org.springframework.statemachine.transition.TransitionEntity;
import org.springframework.statemachine.trigger.Trigger;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class StateMachineResolverImpl<StatesT, EventsT> implements StateMachineResolver<StatesT, EventsT> {

    @Override
    public List<EventsT> getAvailableEvents(StateMachineEntity<StatesT, EventsT> stateMachine) {

        return stateMachine.getTransitions()
                .stream()
                .filter(t -> isTransitionSourceFromCurrentState(t, stateMachine))
                .filter(t -> evaluateGuardCondition(stateMachine, t))
                .map(TransitionEntity::getTrigger)
                .map(Trigger::getEvent)
                .collect(toList());
    }


    private boolean isTransitionSourceFromCurrentState(TransitionEntity<StatesT, EventsT> transition,
                                                       StateMachineEntity<StatesT, EventsT> stateMachine) {

        return stateMachine.getState().getId() == transition.getSource().getId();
    }


    private boolean evaluateGuardCondition(StateMachineEntity<StatesT, EventsT> stateMachine,
                                           TransitionEntity<StatesT, EventsT> transition) {

        if (transition.getGuard() == null) {
            return true;
        }

        StateContext<StatesT, EventsT> context = makeStateContext(stateMachine, transition);

        try {
            return transition.getGuard().evaluate(context);
        } catch (Exception e) {
            return false;
        }
    }


    private DefaultStateContext<StatesT, EventsT> makeStateContext(StateMachineEntity<StatesT, EventsT> stateMachine,
                                                                   TransitionEntity<StatesT, EventsT> transition) {

        return new DefaultStateContext<>(StateContext.Stage.TRANSITION,
                null,
                null,
                stateMachine.getExtendedState(),
                transition,
                stateMachine,
                stateMachine.getState(),
                transition.getTarget(),
                null);
    }
}
*/
