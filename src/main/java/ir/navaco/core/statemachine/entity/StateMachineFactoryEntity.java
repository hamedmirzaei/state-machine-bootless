package ir.navaco.core.statemachine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.navaco.core.statemachine.enums.Schema;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = StateMachineFactoryEntity.STATE_MACHINE_FACTORY_TABLE_NAME, schema = Schema.IF, indexes = {
        @Index(name = "factory_name_idx", columnList = "FACTORY_NAME")
})
public class StateMachineFactoryEntity implements Serializable {

    public static final String STATE_MACHINE_FACTORY_TABLE_NAME = "STATE_MACHINE_FACTORY";
    public static final String STATE_MACHINE_FACTORY_SEQUENCE_NAME = STATE_MACHINE_FACTORY_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "smf_generator")
    @SequenceGenerator(name = "smf_generator", sequenceName = StateMachineFactoryEntity.STATE_MACHINE_FACTORY_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "FACTORY_NAME", nullable = false, unique = true)
    private String factoryName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stateMachineFactory")
    private List<StateEntity> states;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stateMachineFactory")
    private List<TransitionEntity> transitions;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stateMachineFactory")
    private List<StateMachineEntity> stateMachines;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public StateMachineFactoryEntity() {
    }

    public StateMachineFactoryEntity(String factoryName, String description, Boolean active, List<StateEntity> states, List<TransitionEntity> transitions, List<StateMachineEntity> stateMachines) {
        this.factoryName = factoryName;
        this.description = description;
        this.active = active;
        this.states = states;
        this.transitions = transitions;
        this.stateMachines = stateMachines;
    }

    public StateMachineFactoryEntity(Long id, String factoryName, String description, Boolean active, List<StateEntity> states, List<TransitionEntity> transitions, List<StateMachineEntity> stateMachines) {
        this.id = id;
        this.factoryName = factoryName;
        this.description = description;
        this.active = active;
        this.states = states;
        this.transitions = transitions;
        this.stateMachines = stateMachines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<StateEntity> getStates() {
        return states;
    }

    public void setStates(List<StateEntity> states) {
        this.states = states;
    }

    public List<TransitionEntity> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<TransitionEntity> transitions) {
        this.transitions = transitions;
    }

    public List<StateMachineEntity> getStateMachines() {
        return stateMachines;
    }

    public void setStateMachines(List<StateMachineEntity> stateMachines) {
        this.stateMachines = stateMachines;
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

    public StateEntity getInitialState() {
        for (StateEntity state : states) {
            if (state.getInitial())
                return state;
        }
        return null;
    }

    public boolean hasEvent(String event) {
        for (TransitionEntity transition : transitions) {
            if (transition.getEventName().equals(event)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "StateMachineFactoryEntity{" +
                "id=" + id +
                ", factoryName='" + factoryName + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
