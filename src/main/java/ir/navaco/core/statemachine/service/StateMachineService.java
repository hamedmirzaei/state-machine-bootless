package ir.navaco.core.statemachine.service;

import ir.navaco.core.statemachine.exception.StateMachineException;

import java.util.List;

public interface StateMachineService {

    void initialize();
    void deinitialize();

    String createStateMachine(String factoryName)
            throws StateMachineException.FactoryNotFoundException,
            StateMachineException.PersistException;

    String sendEvent(String stateMachineUuid, String event)
            throws StateMachineException.MachineNotExistException,
            StateMachineException.PersistException,
            StateMachineException.EventNotValidException;

    List<String> getEvents(String stateMachineUuid)
            throws StateMachineException.MachineNotExistException;

    List<String> getStates(String stateMachineUuid)
            throws StateMachineException.MachineNotExistException;

    String getCurrentState(String stateMachineUuid)
            throws StateMachineException.MachineNotExistException;
}
