package boot.backend.repository;

import boot.backend.repository.TaskEntity.State;

// tag::code[]
public class TaskSearchCriteria {
    private final State state;
    // end::code[]

    public TaskSearchCriteria(State state) {
        super();
        this.state = state;
    }

    public State getState() {
        return state;
    }
    // tag::code[]
}
// end::code[]