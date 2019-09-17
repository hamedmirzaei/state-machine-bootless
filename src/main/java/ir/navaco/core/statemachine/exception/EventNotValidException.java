package ir.navaco.core.statemachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_FAILURE)
public class EventNotValidException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public EventNotValidException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s with %s : '%s' is not a valid Event", resourceName, fieldName, fieldValue));
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
