package boot.backend.endpoint;

import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.Optional;

import static boot.backend.endpoint.TaskEndpoint.NAMESPACE_URI;
import static boot.backend.repository.TaskEntity.State.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

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
    @Disabled("TODO: Fix test")
    public void testCreateTask() throws IOException {
        Source requestPayload = new StringSource(
                "<as:createTaskRequest xmlns:as=\"" + NAMESPACE_URI + "\">" +
                        "<as:task><as:id>4711</as:id><as:description>TestTask</as:description><as:state>DONE</as:state></as:task>" +
                        "</as:createTaskRequest>");

        Source responsePayload = new StringSource(
                "<as:createTaskResponse xmlns:as=\"" + NAMESPACE_URI + "\">" +
                        "<as:id>4711</as:id>" +
                        "</as:createTaskResponse>");

        Resource xsdSchema = new ClassPathResource("xsd/tasks.xsd");

        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    @Disabled("TODO: Fix test")
    public void testGetTask_Exists() throws IOException {
        Source requestPayload = new StringSource(
                "<as:getTaskRequest xmlns:as=\"" + NAMESPACE_URI + "\">" +
                        "<as:id>4711</as:id>" +
                        "</as:getTaskRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getTaskResponse xmlns:ns2=\"http://www.anderscore.com/soap\">" +
                        "<ns2:task><ns2:id>4711</ns2:id><ns2:description>TestTask</ns2:description><ns2:state>DONE</ns2:state></ns2:task>" +
                        "</ns2:getTaskResponse>");

        Resource xsdSchema = new ClassPathResource("xsd/tasks.xsd");

        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void testGetTask_NotExists() {
        Source requestPayload = new StringSource(
                "<as:getTaskRequest xmlns:as=\"" + NAMESPACE_URI + "\">" +
                        "<as:id>1337</as:id>" +
                        "</as:getTaskRequest>");

        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(serverOrReceiverFault());
    }
}