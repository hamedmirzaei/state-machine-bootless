package ir.navaco.core.statemachine.api;

import ir.navaco.core.statemachine.entity.StateMachineFactoryRequest;
import ir.navaco.core.statemachine.exception.StateMachineFactoryNotFoundException;
import ir.navaco.core.statemachine.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/state-machine")
public class StateMachineController {

    private StateMachineService stateMachineService;

    @Autowired
    public StateMachineController(StateMachineService stateMachineService) {
        this.stateMachineService = stateMachineService;
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
        return stateMachineService.createStateMachine(stateMachineFactoryRequest);
    }


    //TODO sendEvent(sm_id, event)
    //TODO getCurrentState(sm_id)
    //TODO getEvents(sm_id)
    //TODO getStates(sm_id)?

}
