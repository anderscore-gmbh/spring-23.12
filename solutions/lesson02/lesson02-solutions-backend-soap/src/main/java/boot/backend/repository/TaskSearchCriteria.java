package boot.backend.repository;

import boot.backend.repository.TaskEntity.State;

public class TaskSearchCriteria {

    private final State state;

    public TaskSearchCriteria(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}