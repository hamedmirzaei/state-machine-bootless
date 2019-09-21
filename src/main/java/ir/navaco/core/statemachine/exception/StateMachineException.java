package ir.navaco.core.statemachine.exception;

public class StateMachineException {

    // factory not found
    public static class FactoryNotFoundException extends BaseException {
        private String type;

        public FactoryNotFoundException(String type) {
            super("There is no factory with type \"" + type + "\"");
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    // machine not exists
    public static class MachineNotExistException extends BaseException {
        private String stateMachineUuid;

        public MachineNotExistException(String stateMachineUuid) {
            super("There is no machine with uuid \"" + stateMachineUuid + "\"");
            this.stateMachineUuid = stateMachineUuid;
        }

        public String getStateMachineUuid() {
            return stateMachineUuid;
        }
    }

    // failed during persistence
    public static class PersistException extends BaseException {
        private String stateMachineUuid;

        public PersistException(String stateMachineUuid) {
            super("Problem occurred during persisting machine with uuid \"" + stateMachineUuid + "\"");
            this.stateMachineUuid = stateMachineUuid;
        }

        public String getStateMachineUuid() {
            return stateMachineUuid;
        }
    }

    // event is not valid
    public static class EventNotValidException extends BaseException {
        public String eventName;

        public EventNotValidException(String eventName) {
            super("Event with name \"" + eventName + "\" is not a valid event");
            this.eventName = eventName;
        }

        public String getEventName() {
            return eventName;
        }
    }

}
