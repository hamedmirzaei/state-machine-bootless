/*
package ir.navaco.core.statemachine.entity.statemachine;

import com.fasterxml.jackson.annotation.JsonCreator;
import ir.navaco.core.statemachine.exception.StateMachineException;

public enum Event {

    START_FEATURE("START_FEATURE"),
    FINISH_FEATURE("FINISH_FEATURE"),
    QA_REJECTED_UC("QA_REJECTED_UC"),
    ROCK_STAR_DOUBLE_TASK("ROCK_STAR_DOUBLE_TASK"),
    DEPLOY("DEPLOY"),
    QA_CHECKED_UC("QA_CHECKED_UC");

    private String eventName;

    Event(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        return eventName;
    }

    @JsonCreator
    public static Event create(String value) throws StateMachineException.EventNotValidException {
        if (value == null) {
            throw new StateMachineException.EventNotValidException(value);
        }
        for (Event v : values()) {
            if (value.equals(v.getEventName())) {
                return v;
            }
        }
        throw new StateMachineException.EventNotValidException(value);
    }
}
*/
