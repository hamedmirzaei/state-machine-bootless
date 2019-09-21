package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.entity.MyState;
import ir.navaco.core.statemachine.entity.MyStateMachine;
import ir.navaco.core.statemachine.entity.MyStateMachineFactory;
import ir.navaco.core.statemachine.entity.MyTransition;
import ir.navaco.core.statemachine.exception.StateMachineException;
import ir.navaco.core.statemachine.repository.StateMachineFactoryRepository;
import ir.navaco.core.statemachine.repository.StateMachineRepository;
import ir.navaco.core.statemachine.repository.StateRepository;
import ir.navaco.core.statemachine.repository.TransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    @Autowired
    StateMachineFactoryRepository stateMachineFactoryRepository;
    @Autowired
    StateMachineRepository stateMachineRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    TransitionRepository transitionRepository;

    @Override
    public String createStateMachine(String stateMachineFactoryType) throws StateMachineException.FactoryNotFoundException, StateMachineException.PersistException {

        String uuid = "xxx";//TODO UUID.randomUUID().toString();
        MyStateMachineFactory myStateMachineFactory = stateMachineFactoryRepository.findByFactoryName(stateMachineFactoryType);
        if (myStateMachineFactory == null) {
            throw new StateMachineException.FactoryNotFoundException(stateMachineFactoryType);
        }
        MyStateMachine myStateMachine = new MyStateMachine();
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

    @Override
    public String sendEvent(String stateMachineUuid, String event) throws StateMachineException.MachineNotExistException, StateMachineException.PersistException, StateMachineException.EventNotValidException {
        MyStateMachine stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        if (!stateMachine.getStateMachineFactory().hasEvent(event)) {
            throw new StateMachineException.EventNotValidException(event);
        }
        for (MyTransition transition : stateMachine.getStateMachineFactory().getTransitions()) {
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
        MyStateMachine stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        List<String> events = new ArrayList<>();
        for (MyTransition transition : stateMachine.getStateMachineFactory().getTransitions()) {
            events.add(transition.getEventName());
        }
        return events;
    }

    @Override
    public List<String> getStates(String stateMachineUuid) throws StateMachineException.MachineNotExistException {
        MyStateMachine stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        List<String> states = new ArrayList<>();
        for (MyState state : stateMachine.getStateMachineFactory().getStates()) {
            if (!states.contains(state.getStateName()))
                states.add(state.getStateName());
        }
        return states;
    }

    @Override
    public String getCurrentState(String stateMachineUuid) throws StateMachineException.MachineNotExistException {
        MyStateMachine stateMachine = stateMachineRepository.findByUuid(stateMachineUuid);
        if (stateMachine == null) {
            throw new StateMachineException.MachineNotExistException(stateMachineUuid);
        }
        return stateMachine.getCurrentState().getStateName();
    }

    @Override
    public void initialize() {

        MyStateMachineFactory factory = new MyStateMachineFactory();
        factory.setFactoryName("type1");
        factory.setActive(true);
        factory.setDescription("factory type 1");
        stateMachineFactoryRepository.save(factory);
        MyState s1 = new MyState("S1", true, false, factory);
        stateRepository.save(s1);
        MyState s2 = new MyState("S2", false, false, factory);
        stateRepository.save(s2);
        MyState s3 = new MyState("S3", false, true, factory);
        stateRepository.save(s3);
        //factory.setStates(Arrays.asList(s1, s2, s3));

        MyTransition e1 = new MyTransition("E1", s1, s2, factory);
        transitionRepository.save(e1);
        MyTransition e2 = new MyTransition("E2", s2, s3, factory);
        transitionRepository.save(e2);
        //factory.setTransitions(Arrays.asList(e1, e2));
    }

}
