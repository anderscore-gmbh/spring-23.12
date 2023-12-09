package boot.frontend.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import boot.frontend.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
    
    private final RestTemplate restTemplate;
    private final String tasksUrl;

    @Autowired
    public TaskServiceImpl(RestTemplate restTemplate, 
            @Value("${backend.url}") String backendUrl) {
        this.restTemplate = restTemplate;
        this.tasksUrl = backendUrl + "/tasks";
    }

    @Override
    public List<Task> findAll() {
        // tag::generic[]
        ResponseEntity<List<Task>> response = restTemplate.exchange(tasksUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Task>>() {
                });
        List<Task> tasks = response.getBody();
        // end::generic[]
        return tasks;
    }

    @Override
    public Task findById(long taskId) {
        // tag::get[]
        Task task = restTemplate.getForObject(tasksUrl + "/{id}", Task.class, taskId);
        // end::get[]
        return task;
    }

    @Override
    public void create(Task task) {
        // tag::post[]
        URI uri = restTemplate.postForLocation(tasksUrl, task);
        // end::post[]
    }

    @Override
    public void update(Task task) {
        restTemplate.put(tasksUrl + "/{id}", task, task.getId());
    }

    @Override
    public void delete(long taskId) {
        restTemplate.delete(tasksUrl + "/{id}", taskId);
    }
}
