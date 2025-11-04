package be.kdg.sa.water.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BunkerOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime timeScheduled;
    private boolean completed = false;
    @OneToOne
    private DockOperation dockOperation;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimeScheduled() {
        return timeScheduled;
    }

    public void setTimeScheduled(LocalDateTime timeScheduled) {
        this.timeScheduled = timeScheduled;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public DockOperation getDockOperation() {
        return dockOperation;
    }

    public void setDockOperation(DockOperation dockOperation) {
        this.dockOperation = dockOperation;
    }

    public boolean isCompleted() {
        return completed;
    }
}
