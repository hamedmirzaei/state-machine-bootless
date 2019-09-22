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
@Table(name = StateEntity.STATE_TABLE_NAME, schema = Schema.IF)
public class StateEntity implements Serializable {

    public static final String STATE_TABLE_NAME = "STATE";
    public static final String STATE_SEQUENCE_NAME = STATE_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stt_sm_generator")
    @SequenceGenerator(name = "stt_sm_generator", sequenceName = StateEntity.STATE_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "STATE_NAME", nullable = false)
    private String stateName;

    @Column(name = "IS_INITIAL")
    private Boolean initial;

    @Column(name = "IS_END")
    private Boolean end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_MACHINE_FACTORY", referencedColumnName = "ID")
    private StateMachineFactoryEntity stateMachineFactory;

    /*@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceState")
    private List<TransitionEntity> sourceTransitionList;*/

    /*@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinationState")
    private List<TransitionEntity> destinationTransitionList;*/

    /*@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currentState")
    private List<StateMachineEntity> stateMachines;*/

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public StateEntity() {
    }

    public StateEntity(String stateName, Boolean initial, Boolean end, StateMachineFactoryEntity stateMachineFactory) {
        this.stateName = stateName;
        this.initial = initial;
        this.end = end;
        this.stateMachineFactory = stateMachineFactory;
    }

    public StateEntity(Long id, String stateName, Boolean initial, Boolean end, StateMachineFactoryEntity stateMachineFactory) {
        this.id = id;
        this.stateName = stateName;
        this.initial = initial;
        this.end = end;
        this.stateMachineFactory = stateMachineFactory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Boolean getInitial() {
        return initial;
    }

    public void setInitial(Boolean initial) {
        this.initial = initial;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
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
        return "StateEntity{" +
                "id=" + id +
                ", stateName='" + stateName + '\'' +
                ", initial=" + initial +
                ", end=" + end +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        StateEntity m = (StateEntity) obj;
        if(this.stateName == null)
            return false;
        if (this.stateName.equals(m.getStateName())) {
            return true;
        }
        return false;
    }
}
