package boot.backend.endpoint;

import boot.backend.mapper.TaskToMapper;
import boot.backend.repository.TaskRepository;
import com.anderscore.soap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

@Endpoint
public class TaskEndpoint {

    public static final String NAMESPACE_URI = "http://www.anderscore.com/soap";

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskToMapper mapper;

    public CreateTaskResponse createTask(CreateTaskRequest request) {
        // TODO: Implementieren

        throw new UnsupportedOperationException();
    }

    public GetTaskResponse getTask(GetTaskRequest request) {
        // TODO: Implementieren

        throw new UnsupportedOperationException();
    }
}