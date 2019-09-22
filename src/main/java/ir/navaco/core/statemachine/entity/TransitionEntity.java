package ir.navaco.core.statemachine.entity;

import ir.navaco.core.statemachine.enums.Schema;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = TransitionEntity.TRANSITION_TABLE_NAME, schema = Schema.IF)
public class TransitionEntity implements Serializable {

    public static final String TRANSITION_TABLE_NAME = "TRANSITION";
    public static final String TRANSITION_SEQUENCE_NAME = TRANSITION_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trn_sm_generator")
    @SequenceGenerator(name = "trn_sm_generator", sequenceName = TransitionEntity.TRANSITION_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "EVENT_NAME", nullable = false)
    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_STATE", referencedColumnName = "ID")
    private StateEntity sourceState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESTINATION_STATE", referencedColumnName = "ID")
    private StateEntity destinationState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_MACHINE_FACTORY", referencedColumnName = "ID")
    private StateMachineFactoryEntity stateMachineFactory;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public TransitionEntity() {
    }

    public TransitionEntity(String eventName, StateEntity sourceState, StateEntity destinationState, StateMachineFactoryEntity stateMachineFactory) {
        this.eventName = eventName;
        this.sourceState = sourceState;
        this.destinationState = destinationState;
        this.stateMachineFactory = stateMachineFactory;
    }

    public TransitionEntity(Long id, String eventName, StateEntity sourceState, StateEntity destinationState, StateMachineFactoryEntity stateMachineFactory) {
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

    public StateEntity getSourceState() {
        return sourceState;
    }

    public void setSourceState(StateEntity sourceState) {
        this.sourceState = sourceState;
    }

    public StateEntity getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(StateEntity destinationState) {
        this.destinationState = destinationState;
    }

    public StateMachineFactoryEntity getStateMachineFactory() {
        return stateMachineFactory;
    }

    public void setStateMachineFactory(StateMachineFactoryEntity stateMachineFactory) {
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
        return "TransitionEntity{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", sourceState=" + sourceState +
                ", destinationState=" + destinationState +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public boolean isPossible(StateEntity currentState, String event) {
        if (this.sourceState.equals(currentState)) {
            if (this.eventName.equals(event)) {
                return true;
            }
        }
        return false;
    }
}
