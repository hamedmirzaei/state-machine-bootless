package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.entity.StateMachineFactoryRequest;

public interface StateMachineService {

    String createStateMachine(StateMachineFactoryRequest stateMachineFactoryRequest);

}
