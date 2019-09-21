/*
package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.entity.statemachine.Event;
import ir.navaco.core.statemachine.entity.statemachine.State;
import ir.navaco.core.statemachine.exception.StateMachineException;
import ir.navaco.core.statemachine.repository.MyJpaStateMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateMachineServiceImpl2 implements StateMachineService {

    @Autowired
    private MyJpaStateMachineRepository myJpaStateMachineRepository;

    @Autowired
    private StateMachineFactory<State, Event> stateMachineFactory;

    @Autowired
    private StateMachineFactory<State, Event> secondStateMachineFactory;

    @Autowired
    private StateMachinePersister<State, Event, String> persister;

    @Override
    public String createStateMachine(String stateMachineFactoryType)
            throws StateMachineException.FactoryNotFoundException, StateMachineException.PersistException {
        String stateMachineId = "xxx"; //TODO replace with UUID.randomUUID().toString();
        StateMachineFactory<State, Event> factory = getStateMachineFactory(stateMachineFactoryType);
        StateMachine<State, Event> stateMachine = factory.getStateMachine(stateMachineId);
        try {
            persister.persist(stateMachine, stateMachine.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new StateMachineException.PersistException(stateMachineId);
        }
        return stateMachineId;
    }

    private StateMachineFactory<State, Event> getStateMachineFactory(String stateMachineFactoryType)
            throws StateMachineException.FactoryNotFoundException {
        switch (stateMachineFactoryType) {
            case "type1":
                return stateMachineFactory;
            case "type2":
                return secondStateMachineFactory;
            default:
                throw new StateMachineException.FactoryNotFoundException(stateMachineFactoryType);
        }
    }

    @Override
    public String sendEvent(String stateMachineId, String eventStr)
            throws StateMachineException.MachineNotExistException, StateMachineException.PersistException, StateMachineException.EventNotValidException {
        if (myJpaStateMachineRepository.findByMachineId(stateMachineId) == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineId);
        }
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
            throw new StateMachineException.PersistException(stateMachineId);
        }
        return "Event sent successfully";
    }

    @Override
    public List<Event> getEvents(String stateMachineId) throws StateMachineException.MachineNotExistException {
        if (myJpaStateMachineRepository.findByMachineId(stateMachineId) == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineId);
        }
        return null;
    }
}
*/
