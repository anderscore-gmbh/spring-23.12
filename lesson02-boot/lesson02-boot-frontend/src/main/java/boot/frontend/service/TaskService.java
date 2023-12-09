package boot.frontend.service;

import java.util.List;

import jakarta.validation.Valid;

import boot.frontend.model.Task;

public interface TaskService {

    List<Task> findAll();

    Task findById(long taskId);

    void create(@Valid Task task);

    void update(@Valid Task task);

    void delete(long taskId);

}
