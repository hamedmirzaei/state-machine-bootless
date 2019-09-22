package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.entity.StateEntity;
import ir.navaco.core.statemachine.entity.StateMachineEntity;
import ir.navaco.core.statemachine.entity.StateMachineFactoryEntity;
import ir.navaco.core.statemachine.entity.TransitionEntity;
import ir.navaco.core.statemachine.exception.StateMachineException;
import ir.navaco.core.statemachine.repository.StateMachineFactoryRepository;
import ir.navaco.core.statemachine.repository.StateMachineRepository;
import ir.navaco.core.statemachine.repository.StateRepository;
import ir.navaco.core.statemachine.repository.TransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    private StateMachineFactoryRepository stateMachineFactoryRepository;
    private StateMachineRepository stateMachineRepository;
    private StateRepository stateRepository;
    private TransitionRepository transitionRepository;

    @Transactional
    @Override
    public String createStateMachine(String factoryName) throws StateMachineException.FactoryNotFoundException, StateMachineException.PersistException {
        String uuid = "xxx";//TODO UUID.randomUUID().toString();
        StateMachineFactoryEntity myStateMachineFactory = stateMachineFactoryRepository.findByFactoryName(factoryName);
        if (myStateMachineFactory == null) {
            throw new StateMachineException.FactoryNotFoundException(factoryName);
        }
        StateMachineEntity myStateMachine = new StateMachineEntity();
        myStateMachine.setStateMachineFactory(myStateMachineFactory);
        myStateMachine.setUuid(uuid);
        myStateMachine.setCurrentState(myStateMachineFactory.getInitialState());
        try {
            stateMachineRepository.save(myStateMachine);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new StateMachineException.PersistException(uuid);
        }
        return uuid;
    }

    @Transactional
    @Override
    public String sendEvent(String stateMachineUuid, String event) throws StateMachineException.MachineNotExistException, StateMachineException.PersistException, StateMachineException.EventNotValidException {
        StateMachineEntity stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        if (!stateMachine.getStateMachineFactory().hasEvent(event)) {
            throw new StateMachineException.EventNotValidException(event);
        }
        for (TransitionEntity transition : stateMachine.getStateMachineFactory().getTransitions()) {
            if (transition.isPossible(stateMachine.getCurrentState(), event)) {
                stateMachine.setCurrentState(transition.getDestinationState());
                try {
                    stateMachineRepository.save(stateMachine);
                    break;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new StateMachineException.PersistException(stateMachineUuid);
                }
            }
        }
        return "Event \"" + event + "\" received successfully";
    }

    @Override
    public List<String> getEvents(String stateMachineUuid) throws StateMachineException.MachineNotExistException {
        StateMachineEntity stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        List<String> events = new ArrayList<>();
        for (TransitionEntity transition : stateMachine.getStateMachineFactory().getTransitions()) {
            events.add(transition.getEventName());
        }
        return events;
    }

    @Override
    public List<String> getStates(String stateMachineUuid) throws StateMachineException.MachineNotExistException {
        StateMachineEntity stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        List<String> states = new ArrayList<>();
        for (StateEntity state : stateMachine.getStateMachineFactory().getStates()) {
            if (!states.contains(state.getStateName()))
                states.add(state.getStateName());
        }
        return states;
    }

    @Override
    public String getCurrentState(String stateMachineUuid) throws StateMachineException.MachineNotExistException {
        StateMachineEntity stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        return stateMachine.getCurrentState().getStateName();
    }

    @Autowired
    public void setStateMachineFactoryRepository(StateMachineFactoryRepository stateMachineFactoryRepository) {
        this.stateMachineFactoryRepository = stateMachineFactoryRepository;
    }

    @Autowired
    public void setStateMachineRepository(StateMachineRepository stateMachineRepository) {
        this.stateMachineRepository = stateMachineRepository;
    }

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Autowired
    public void setTransitionRepository(TransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    @Override
    public void initialize() {
        StateMachineFactoryEntity factory = new StateMachineFactoryEntity();
        factory.setFactoryName("type1");
        factory.setActive(true);
        factory.setDescription("factory type 1");
        stateMachineFactoryRepository.save(factory);
        StateEntity s1 = new StateEntity("S1", true, false, factory);
        stateRepository.save(s1);
        StateEntity s2 = new StateEntity("S2", false, false, factory);
        stateRepository.save(s2);
        StateEntity s3 = new StateEntity("S3", false, true, factory);
        stateRepository.save(s3);
        //factory.setStates(Arrays.asList(s1, s2, s3));

        TransitionEntity e1 = new TransitionEntity("E1", s1, s2, factory);
        transitionRepository.save(e1);
        TransitionEntity e2 = new TransitionEntity("E2", s2, s3, factory);
        transitionRepository.save(e2);
        //factory.setTransitions(Arrays.asList(e1, e2));
    }

}
