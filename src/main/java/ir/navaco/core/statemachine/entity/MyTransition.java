package ir.navaco.core.statemachine.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = MyTransition.TRANSITION_TABLE_NAME, schema = "USRPRF")
public class MyTransition implements Serializable {

    public static final String TRANSITION_TABLE_NAME = "MY_TRANSITION";
    public static final String TRANSITION_SEQUENCE_NAME = TRANSITION_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trn_sm_generator")
    @SequenceGenerator(name = "trn_sm_generator", sequenceName = MyTransition.TRANSITION_SEQUENCE_NAME, schema = "USRPRF", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "EVENT_NAME", nullable = false)
    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_STATE", referencedColumnName = "ID")
    private MyState sourceState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESTINATION_STATE", referencedColumnName = "ID")
    private MyState destinationState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_MACHINE_FACTORY", referencedColumnName = "ID")
    private MyStateMachineFactory stateMachineFactory;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public MyTransition() {
    }

    public MyTransition(String eventName, MyState sourceState, MyState destinationState, MyStateMachineFactory stateMachineFactory) {
        this.eventName = eventName;
        this.sourceState = sourceState;
        this.destinationState = destinationState;
        this.stateMachineFactory = stateMachineFactory;
    }

    public MyTransition(Long id, String eventName, MyState sourceState, MyState destinationState, MyStateMachineFactory stateMachineFactory) {
        this.id = id;
        this.eventName = eventName;
        this.sourceState = sourceState;
        this.destinationState = destinationState;
        this.stateMachineFactory = stateMachineFactory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public MyState getSourceState() {
        return sourceState;
    }

    public void setSourceState(MyState sourceState) {
        this.sourceState = sourceState;
    }

    public MyState getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(MyState destinationState) {
        this.destinationState = destinationState;
    }

    public MyStateMachineFactory getStateMachineFactory() {
        return stateMachineFactory;
    }

    public void setStateMachineFactory(MyStateMachineFactory stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "MyTransition{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", sourceState=" + sourceState +
                ", destinationState=" + destinationState +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public boolean isPossible(MyState currentState, String event) {
        if (this.sourceState.equals(currentState)) {
            if (this.eventName.equals(event)) {
                return true;
            }
        }
        return false;
    }
}
