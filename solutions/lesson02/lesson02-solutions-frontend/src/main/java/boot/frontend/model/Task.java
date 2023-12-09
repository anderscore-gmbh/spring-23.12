package boot.frontend.model;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;

public class Task {
    public enum State {
        OPEN, STARTED, DONE
    }

    private Long id;
    @Size(min = 6, max = 40)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Date dateDue;
    private State state;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateDue == null) ? 0 : dateDue.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (dateDue == null) {
            if (other.dateDue != null)
                return false;
        } else if (!dateDue.equals(other.dateDue))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (state != other.state)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", description='" + description + '\'' + ", dateDue=" + dateDue + ", state="
                + state + '}';
    }

    public boolean isNew() {
        return id == null;
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

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
