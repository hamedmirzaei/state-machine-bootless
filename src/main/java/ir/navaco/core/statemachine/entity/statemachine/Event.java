package ir.navaco.core.statemachine.entity.statemachine;

import com.fasterxml.jackson.annotation.JsonCreator;
import ir.navaco.core.statemachine.exception.EventNotValidException;

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
    public static Event create(String value) throws EventNotValidException {
        if(value == null) {
            throw new EventNotValidException("Event", "eventName", value);
        }
        for(Event v : values()) {
            if(value.equals(v.getEventName())) {
                return v;
            }
        }
        throw new EventNotValidException("Event", "eventName", value);
    }
}
