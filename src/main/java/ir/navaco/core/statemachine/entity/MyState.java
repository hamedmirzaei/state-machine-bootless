package ir.navaco.core.statemachine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = MyState.STATE_TABLE_NAME, schema = "USRPRF")
public class MyState implements Serializable {

    public static final String STATE_TABLE_NAME = "MY_STATE";
    public static final String STATE_SEQUENCE_NAME = STATE_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stt_sm_generator")
    @SequenceGenerator(name = "stt_sm_generator", sequenceName = MyState.STATE_SEQUENCE_NAME, schema = "USRPRF", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "STATE_NAME", nullable = false)
    private String stateName;

    @Column(name = "IS_INITIAL")
    private Boolean initial;

    @Column(name = "IS_END")
    private Boolean end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_MACHINE_FACTORY", referencedColumnName = "ID")
    private MyStateMachineFactory stateMachineFactory;

    /*@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceState")
    private List<MyTransition> sourceTransitionList;*/

    /*@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinationState")
    private List<MyTransition> destinationTransitionList;*/

    /*@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currentState")
    private List<MyStateMachine> stateMachines;*/

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public MyState() {
    }

    public MyState(String stateName, Boolean initial, Boolean end, MyStateMachineFactory stateMachineFactory) {
        this.stateName = stateName;
        this.initial = initial;
        this.end = end;
        this.stateMachineFactory = stateMachineFactory;
    }

    public MyState(Long id, String stateName, Boolean initial, Boolean end, MyStateMachineFactory stateMachineFactory) {
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
        return "MyState{" +
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
        MyState m = (MyState) obj;
        if(this.stateName == null)
            return false;
        if (this.stateName.equals(m.getStateName())) {
            return true;
        }
        return false;
    }
}
