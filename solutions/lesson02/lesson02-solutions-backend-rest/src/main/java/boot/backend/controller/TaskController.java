package boot.backend.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskEntity.State;
import boot.backend.repository.TaskRepository;
import boot.backend.repository.TaskSearchCriteria;
import boot.backend.repository.TaskSpecification;

@RestController
@Transactional
@RequestMapping("/tasks")
public class TaskController {
    private static final String PATH = "/tasks/{id}";

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "", params = "!state")
    public List<TaskEntity> findAllTasks() {
        // TODO: Liste aller Tasks zurückgeben
        List<TaskEntity> tasks = taskRepository.findAll();
        return tasks;
    }

    @GetMapping(path = "", params = "state")
    public List<TaskEntity> findTasks(@RequestParam State state) {
        // TODO: Liste der passenden Tasks zurückgeben
        TaskSearchCriteria criteria = new TaskSearchCriteria(state);
        TaskSpecification spec = new TaskSpecification(criteria);
        List<TaskEntity> tasks = taskRepository.findAll(spec);
        return tasks;
    }

    @GetMapping("/{id}")
    public TaskEntity findTask(@PathVariable long id) {
        // TODO: Ein Task laden
        // TODO: Fehler 404, wenn es dieses Task nicht gibt
        Optional<TaskEntity> task = taskRepository.findById(id);
        return task.orElseThrow(() -> new NotFoundException(id));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody TaskEntity task, UriComponentsBuilder uriBuilder,
            final HttpServletResponse response) {
        // TODO: Ein neues Task anlegen
        // TODO: Fehler 409, falls id != null
        // TODO: "Location" Header mit Link zum erzeugten Task setzen
        // TODO: Status 201 bei Erfolg
        if (task.getId() != null) {
            throw new ConflictException("Create with id=" + task.getId() + " called.");
        }
        TaskEntity persisted = taskRepository.save(task);
        String locationValue = uriBuilder.path(PATH).build().expand(persisted.getId()).encode().toUriString();
        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable long id, @RequestBody TaskEntity task) {
        // TODO: Task ändern
        // TODO: Fehler 404, wenn es dieses Task nicht gibt
        // TODO: Fehler 409, falls id != task.id
        if (!Objects.equals(id, task.getId())) {
            throw new ConflictException("Update with id=" + task.getId() + " for id=" + id + " called.");
        }
        taskRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {
        // TODO: Task löschen, falls vorhanden
        // TODO: Fehler 404, wenn es dieses Task nicht gibt
        // TODO: Status 204 bei Erfolg
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
        } else {
            throw new NotFoundException(id);
        }
    }
}
