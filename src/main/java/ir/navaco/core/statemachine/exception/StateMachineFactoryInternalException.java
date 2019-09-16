package ir.navaco.core.statemachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_FAILURE)
public class StateMachineFactoryInternalException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public StateMachineFactoryInternalException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s with %s : '%s' failed to be deleted", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
