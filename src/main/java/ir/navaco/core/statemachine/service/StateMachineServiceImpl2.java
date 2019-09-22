/*
package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.entity.statemachine.Event;
import ir.navaco.core.statemachine.entity.statemachine.StateEntity;
import ir.navaco.core.statemachine.exception.StateMachineException;
import ir.navaco.core.statemachine.repository.MyJpaStateMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachineEntity;
import org.springframework.statemachine.config.StateMachineFactoryEntity;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateMachineServiceImpl2 implements StateMachineService {

    @Autowired
    private MyJpaStateMachineRepository myJpaStateMachineRepository;

    @Autowired
    private StateMachineFactoryEntity<StateEntity, Event> stateMachineFactory;

    @Autowired
    private StateMachineFactoryEntity<StateEntity, Event> secondStateMachineFactory;

    @Autowired
    private StateMachinePersister<StateEntity, Event, String> persister;

    @Override
    public String createStateMachine(String stateMachineFactoryType)
            throws StateMachineException.FactoryNotFoundException, StateMachineException.PersistException {
        String stateMachineId = "xxx"; //TODO replace with UUID.randomUUID().toString();
        StateMachineFactoryEntity<StateEntity, Event> factory = getStateMachineFactory(stateMachineFactoryType);
        StateMachineEntity<StateEntity, Event> stateMachine = factory.getStateMachine(stateMachineId);
        try {
            persister.persist(stateMachine, stateMachine.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new StateMachineException.PersistException(stateMachineId);
        }
        return stateMachineId;
    }

    private StateMachineFactoryEntity<StateEntity, Event> getStateMachineFactory(String stateMachineFactoryType)
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
        StateMachineEntity<StateEntity, Event> stateMachine = stateMachineFactory.getStateMachine(stateMachineId + "_test");
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
