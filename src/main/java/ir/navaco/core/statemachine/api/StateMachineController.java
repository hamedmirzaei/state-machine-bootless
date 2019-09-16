package ir.navaco.core.statemachine.api;

import ir.navaco.core.statemachine.entity.StateMachineFactoryRequest;
import ir.navaco.core.statemachine.entity.statemachine.Events;
import ir.navaco.core.statemachine.entity.statemachine.States;
import ir.navaco.core.statemachine.exception.StateMachineFactoryInternalException;
import ir.navaco.core.statemachine.exception.StateMachineFactoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/state-machine")
public class StateMachineController {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    @Autowired
    private StateMachinePersister<States, Events, String> persister;

    @Autowired
    public StateMachineController() {
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Server is UP";
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public StateMachineFactoryRequest getStateMachine() {
        return new StateMachineFactoryRequest("Hello");
    }

    //TODO sm_id createStateMachine(factoryType)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createStateMachine(@RequestBody StateMachineFactoryRequest stateMachineFactoryRequest) throws StateMachineFactoryNotFoundException {
        String stateMachineUUID = UUID.randomUUID().toString();
        switch (stateMachineFactoryRequest.getType()) {
            case "type1":
                StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(stateMachineUUID);
                try {
                    persister.persist(stateMachine, stateMachine.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new StateMachineFactoryInternalException("StateMachineController", "stateMachine.getId()", stateMachine.getId());
                }
                break;
            default:
                throw new StateMachineFactoryNotFoundException("StateMachineFactoryRequest", "type", stateMachineFactoryRequest.getType());
        }
        return stateMachineUUID;
    }


    //TODO sendEvent(sm_id, event)
    //TODO getCurrentState(sm_id)
    //TODO getEvents(sm_id)
    //TODO getStates(sm_id)?

}
