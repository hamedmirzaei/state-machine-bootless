package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.entity.statemachine.Event;
import ir.navaco.core.statemachine.entity.statemachine.State;
import ir.navaco.core.statemachine.exception.EventNotValidException;
import ir.navaco.core.statemachine.exception.StateMachineFactoryNotFoundException;
import ir.navaco.core.statemachine.exception.StateMachineNotFoundException;
import ir.navaco.core.statemachine.exception.StateMachinePersistInternalException;
import ir.navaco.core.statemachine.repository.MyJpaStateMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    @Autowired
    private MyJpaStateMachineRepository myJpaStateMachineRepository;

    @Autowired
    private StateMachineFactory<State, Event> stateMachineFactory;

    @Autowired
    private StateMachinePersister<State, Event, String> persister;

    @Override
    public String createStateMachine(String stateMachineFactoryType) {
        String stateMachineId = "xxx"; //UUID.randomUUID().toString();
        StateMachine<State, Event> stateMachine = null;
        switch (stateMachineFactoryType) {
            case "type1":
                stateMachine = stateMachineFactory.getStateMachine(stateMachineId);
                break;
            default:
                throw new StateMachineFactoryNotFoundException("StateMachineFactory", "type", stateMachineFactoryType);
        }
        try {
            persister.persist(stateMachine, stateMachine.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new StateMachinePersistInternalException("StateMachine", "stateMachineId", stateMachine.getId());
        }
        return stateMachineId;
    }

    @Override
    public String sendEvent(String stateMachineId, String eventStr)
            throws StateMachinePersistInternalException, StateMachineNotFoundException, EventNotValidException {
        if (myJpaStateMachineRepository.findByMachineId(stateMachineId) != null) {
            StateMachine<State, Event> stateMachine = stateMachineFactory.getStateMachine(stateMachineId + "_test");
            try {
                persister.restore(stateMachine, stateMachineId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Event event = Event.create(eventStr);
            stateMachine.sendEvent(event);
            try {
                persister.persist(stateMachine, stateMachine.getId());
            } catch (Exception e) {
                e.printStackTrace();
                throw new StateMachinePersistInternalException("StateMachineController", "stateMachineId", stateMachineId);
            }
        } else {
            throw new StateMachineNotFoundException("StateMachine", "stateMachineId", stateMachineId);
        }
        return "Event sent successfully";
    }
}
