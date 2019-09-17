package ir.navaco.core.statemachine.api;

import ir.navaco.core.statemachine.exception.EventNotValidException;
import ir.navaco.core.statemachine.exception.StateMachineFactoryNotFoundException;
import ir.navaco.core.statemachine.exception.StateMachineNotFoundException;
import ir.navaco.core.statemachine.exception.StateMachinePersistInternalException;
import ir.navaco.core.statemachine.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * It will create a state machine instance of type stateMachineFactoryType and
     * return its id for further usage like sending events
     *
     * @param input type of factory to create state machine from
     * @return state machine id
     * @throws StateMachineFactoryNotFoundException where the type is not a valid factory type
     */
    //TODO sm_id createStateMachine(factoryType)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStateMachine(@RequestBody Map<String, String> input) {
        String stateMachineId = "";
        try {
            stateMachineId = stateMachineService.createStateMachine(input.get("stateMachineFactoryType"));
            return ResponseEntity.ok(stateMachineId);
        } catch (StateMachineFactoryNotFoundException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    /**
     * sends an event
     *
     * @param input
     * @return
     * @throws StateMachinePersistInternalException
     * @throws StateMachineNotFoundException
     * @throws EventNotValidException
     */
    //TODO sendEvent(sm_id, event)
    @PostMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEvent(@RequestBody Map<String, String> input)
            throws StateMachinePersistInternalException, StateMachineNotFoundException, EventNotValidException {
        try {
            String message = stateMachineService.sendEvent(input.get("stateMachineId"), input.get("event"));
            return ResponseEntity.ok(message);
        } catch (StateMachinePersistInternalException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        } catch (StateMachineNotFoundException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (EventNotValidException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    //TODO getCurrentState(sm_id)
    //TODO getEvents(sm_id)
    //TODO getStates(sm_id)?

}
