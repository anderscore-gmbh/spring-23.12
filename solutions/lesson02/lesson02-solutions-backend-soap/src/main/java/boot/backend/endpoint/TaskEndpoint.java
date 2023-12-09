package boot.backend.endpoint;

import boot.backend.exception.NotFoundException;
import boot.backend.mapper.TaskToMapper;
import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskRepository;
import com.anderscore.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class TaskEndpoint {

    public static final String NAMESPACE_URI = "http://www.anderscore.com/soap";

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskToMapper mapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createTaskRequest")
    @ResponsePayload
    public CreateTaskResponse createTask(@RequestPayload CreateTaskRequest request) {
        TaskTo taskTo = request.getTask();
        TaskEntity taskEntity = mapper.asTaskEntity(taskTo);

        taskEntity = taskRepository.save(taskEntity);

        CreateTaskResponse response = new CreateTaskResponse();
        response.setId(taskEntity.getId());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTaskRequest")
    @ResponsePayload
    public GetTaskResponse getTask(@RequestPayload GetTaskRequest request) {
        Optional<TaskEntity> entity = taskRepository.findById(request.getId());

        if (entity.isPresent()){
            TaskTo task = mapper.asTaskTo(entity.get());

            GetTaskResponse response = new GetTaskResponse();
            response.setTask(task);

            return response;

        } else {
            throw new NotFoundException(request.getId());
        }
    }
}