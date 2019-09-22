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
@Table(name = StateMachineEntity.STATE_MACHINE_TABLE_NAME, schema = Schema.IF)
public class StateMachineEntity implements Serializable {

    public static final String STATE_MACHINE_TABLE_NAME = "STATE_MACHINE";
    public static final String STATE_MACHINE_SEQUENCE_NAME = STATE_MACHINE_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sm_generator")
    @SequenceGenerator(name = "sm_generator", sequenceName = StateMachineEntity.STATE_MACHINE_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "UUID", nullable = false, unique = true)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_MACHINE_FACTORY", referencedColumnName = "ID")
    private StateMachineFactoryEntity stateMachineFactory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENT_STATE", referencedColumnName = "ID")
    private StateEntity currentState;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public StateMachineEntity() {
    }

    public StateMachineEntity(String uuid, StateMachineFactoryEntity stateMachineFactory, StateEntity currentState) {
        this.uuid = uuid;
        this.stateMachineFactory = stateMachineFactory;
        this.currentState = currentState;
    }

    public StateMachineEntity(Long id, String uuid, StateMachineFactoryEntity stateMachineFactory, StateEntity currentState) {
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

    public StateMachineFactoryEntity getStateMachineFactory() {
        return stateMachineFactory;
    }

    public void setStateMachineFactory(StateMachineFactoryEntity stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    public StateEntity getCurrentState() {
        return currentState;
    }

    public void setCurrentState(StateEntity currentState) {
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
        return "StateMachineEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", currentState=" + currentState +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
