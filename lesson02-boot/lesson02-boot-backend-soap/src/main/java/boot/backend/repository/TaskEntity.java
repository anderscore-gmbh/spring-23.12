package boot.backend.repository;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Objekt in eigener Datenbanktabelle speichern
@Table(name = "TASK") // Tabellename
public class TaskEntity {

    public enum State {
        OPEN, STARTED, DONE
    }

    @Id // Primärschlüssel
    @GeneratedValue // wird automatisch generiert
    @Column(name = "TASK_ID") // expliziter Spaltenname
    private Long id;

    private String description;
    private Instant dateDue;

    @Enumerated(EnumType.STRING) // als String speichern
    private State state;

    public static TaskEntity copyOf(TaskEntity task) {
        TaskEntity copy = new TaskEntity();
        copy.setId(task.getId());
        copy.setDescription(task.getDescription());
        copy.setState(task.getState());
        copy.setDateDue(task.getDateDue());

        return copy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TaskEntity other = (TaskEntity) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", description='" + description + "', dateDue=" + dateDue + ", state=" + state
                + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateDue() {
        return dateDue;
    }

    public void setDateDue(Instant dateDue) {
        this.dateDue = dateDue;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}