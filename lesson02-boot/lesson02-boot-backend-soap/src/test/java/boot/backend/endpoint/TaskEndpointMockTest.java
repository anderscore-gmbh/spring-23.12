package boot.backend.endpoint;

import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.Optional;

import static boot.backend.repository.TaskEntity.State.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;

@SpringBootTest
public class TaskEndpointMockTest {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private TaskRepository taskRepository;

    private MockWebServiceClient mockClient;

    @BeforeEach
    public void init() {
        TaskEntity entity = new TaskEntity();
        entity.setId(4711L);
        entity.setDescription("TestTask");
        entity.setState(DONE);

        when(taskRepository.findById(4711L)).thenReturn(Optional.of(entity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(entity);

        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void testCreateTask() throws IOException {
        Source requestPayload = new StringSource(
                "<ns2:createTaskRequest xmlns:ns2=\"http://www.anderscore.com/soap\">" +
                        "<ns2:task><ns2:id>4711</ns2:id><ns2:description>TestTask</ns2:description><ns2:state>DONE</ns2:state></ns2:task>" +
                        "</ns2:createTaskRequest>");

        // TODO: Request versenden, Response prüfen
    }

    @Test
    public void testGetTask_Exists() throws IOException {
        Source requestPayload = new StringSource(
                "<as:getTaskRequest xmlns:as=\"http://www.anderscore.com/soap\">" +
                        "<as:id>4711</as:id>" +
                        "</as:getTaskRequest>");

        // TODO: Request versenden, Response prüfen
    }

    @Test
    public void testGetTask_NotExists() {
        Source requestPayload = new StringSource(
                "<as:getTaskRequest xmlns:as=\"http://www.anderscore.com/soap\">" +
                        "<as:id>1337</as:id>" +
                        "</as:getTaskRequest>");

        // TODO: Request versenden, Response prüfen
    }
}