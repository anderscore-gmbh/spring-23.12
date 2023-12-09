package boot.frontend.service;

import java.util.List;


import boot.frontend.model.Task;
import jakarta.validation.Valid;

public interface TaskService {

    List<Task> findAll();

    Task findById(long taskId);

    void create(@Valid Task task);

    void update(@Valid Task task);

    void delete(long taskId);

}
