package ir.navaco.core.statemachine.service;

public interface StateMachineService {

    String createStateMachine(String stateMachineFactoryType);

    String sendEvent(String stateMachineId, String event);
}
