package ir.navaco.core.statemachine.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = MyStateMachine.STATE_MACHINE_TABLE_NAME, schema = "USRPRF")
public class MyStateMachine implements Serializable {

    public static final String STATE_MACHINE_TABLE_NAME = "MY_STATE_MACHINE";
    public static final String STATE_MACHINE_SEQUENCE_NAME = STATE_MACHINE_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sm_generator")
    @SequenceGenerator(name = "sm_generator", sequenceName = MyStateMachine.STATE_MACHINE_SEQUENCE_NAME, schema = "USRPRF", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "UUID", nullable = false, unique = true)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_MACHINE_FACTORY", referencedColumnName = "ID")
    private MyStateMachineFactory stateMachineFactory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_STATE", referencedColumnName = "ID")
    private MyState currentState;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public MyStateMachine() {
    }

    public MyStateMachine(String uuid, MyStateMachineFactory stateMachineFactory, MyState currentState) {
        this.uuid = uuid;
        this.stateMachineFactory = stateMachineFactory;
        this.currentState = currentState;
    }

    public MyStateMachine(Long id, String uuid, MyStateMachineFactory stateMachineFactory, MyState currentState) {
        this.id = id;
        this.uuid = uuid;
        this.stateMachineFactory = stateMachineFactory;
        this.currentState = currentState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public MyStateMachineFactory getStateMachineFactory() {
        return stateMachineFactory;
    }

    public void setStateMachineFactory(MyStateMachineFactory stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    public MyState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MyState currentState) {
        this.currentState = currentState;
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
        return "MyStateMachine{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", stateMachineFactory=" + stateMachineFactory +
                ", currentState=" + currentState +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
