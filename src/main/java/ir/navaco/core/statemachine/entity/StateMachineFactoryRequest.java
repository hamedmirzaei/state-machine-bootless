package ir.navaco.core.statemachine.entity;

import java.io.Serializable;

public class StateMachineFactoryRequest implements Serializable {

    String type;

    public StateMachineFactoryRequest() {
    }

    public StateMachineFactoryRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StateMachineFactoryRequest{" +
                "type='" + type + '\'' +
                '}';
    }
}
