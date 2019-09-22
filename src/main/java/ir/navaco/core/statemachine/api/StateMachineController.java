package ir.navaco.core.statemachine.api;

import ir.navaco.core.statemachine.exception.StateMachineException;
import ir.navaco.core.statemachine.exception.StateMachineRequestValidationException;
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
        stateMachineService.initialize();
        return "Server is UP";
    }

    /**
     * It will create a state machine instance of type stateMachineFactoryType and
     * return its UUID for further usage like sending events
     *
     * @param input type of factory to create state machine from
     * @return UUID of state machine
     */
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStateMachine(@RequestBody Map<String, String> input) {
        try {
            validate(input, 1, "factoryName");
            String stateMachineUuid = stateMachineService.createStateMachine(input.get("factoryName"));
            return ResponseEntity.ok(stateMachineUuid);
        } catch (StateMachineRequestValidationException.BadSizeMap | StateMachineRequestValidationException.FieldNotExist ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (StateMachineException.FactoryNotFoundException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (StateMachineException.PersistException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * this returns list of all events of a state machine
     *
     * @param stateMachineUuid UUID of state machine
     * @return list of all events
     */
    @GetMapping("/{smuuid}/events")
    public ResponseEntity<Object> getEvents(@PathVariable("smuuid") String stateMachineUuid) {
        try {
            return ResponseEntity.ok(stateMachineService.getEvents(stateMachineUuid));
        } catch (StateMachineException.MachineNotExistException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    /**
     * this triggers an event on the state machine
     *
     * @param stateMachineUuid UUID of state machine
     * @param input            body of POST message which is the event
     * @return a success or failure message
     */
    @PostMapping(value = "/{smuuid}/events/trigger", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEvent(@PathVariable("smuuid") String stateMachineUuid, @RequestBody Map<String, String> input) {
        try {
            validate(input, 1, "eventName");
            String message = stateMachineService.sendEvent(stateMachineUuid, input.get("eventName"));
            return ResponseEntity.ok(message);
        } catch (StateMachineRequestValidationException.BadSizeMap | StateMachineRequestValidationException.FieldNotExist | StateMachineException.EventNotValidException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (StateMachineException.PersistException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        } catch (StateMachineException.MachineNotExistException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    /**
     * this returns list of all states of a state machine
     *
     * @param stateMachineUuid UUID of state machine
     * @return list of all states
     */
    @GetMapping("/{smuuid}/states")
    public ResponseEntity<Object> getStates(@PathVariable("smuuid") String stateMachineUuid) {
        try {
            return ResponseEntity.ok(stateMachineService.getStates(stateMachineUuid));
        } catch (StateMachineException.MachineNotExistException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    /**
     * this returns the current state of a state machine
     *
     * @param stateMachineUuid UUID of state machine
     * @return current state
     */
    @GetMapping("/{smuuid}/states/current")
    public ResponseEntity<String> getCurrentState(@PathVariable("smuuid") String stateMachineUuid) {
        try {
            return ResponseEntity.ok(stateMachineService.getCurrentState(stateMachineUuid));
        } catch (StateMachineException.MachineNotExistException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    private void validate(Map<String, String> input, Integer size, String... keys)
            throws StateMachineRequestValidationException.BadSizeMap, StateMachineRequestValidationException.FieldNotExist {
        if (input.size() != size) {
            throw new StateMachineRequestValidationException.BadSizeMap(size, input.size());
        }
        for (String key : keys) {
            if (!input.containsKey(key)) {
                throw new StateMachineRequestValidationException.FieldNotExist(key);
            }
        }
    }
}
