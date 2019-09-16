package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.entity.StateMachineFactoryRequest;
import ir.navaco.core.statemachine.entity.statemachine.Events;
import ir.navaco.core.statemachine.entity.statemachine.States;
import ir.navaco.core.statemachine.exception.StateMachineFactoryInternalException;
import ir.navaco.core.statemachine.exception.StateMachineFactoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    @Autowired
    private StateMachinePersister<States, Events, String> persister;

    @Override
    public String createStateMachine(StateMachineFactoryRequest stateMachineFactoryRequest) {
        String stateMachineUUID = UUID.randomUUID().toString();
        StateMachine<States, Events> stateMachine = null;
        switch (stateMachineFactoryRequest.getType()) {
            case "type1":
                stateMachine = stateMachineFactory.getStateMachine(stateMachineUUID);
                break;
            default:
                throw new StateMachineFactoryNotFoundException("StateMachineFactoryRequest", "type", stateMachineFactoryRequest.getType());
        }
        try {
            persister.persist(stateMachine, stateMachine.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new StateMachineFactoryInternalException("StateMachineController", "stateMachine.getId()", stateMachine.getId());
        }
        return stateMachineUUID;
    }
}
