package boot.backend.endpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static boot.backend.endpoint.TaskEndpoint.NAMESPACE_URI;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

@SpringBootTest
public class TaskEndpointIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @BeforeEach
    public void init() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void testCreateAndGetTask() {
        Source requestPayload = new StringSource(
                "<as:createTaskRequest xmlns:as=\"" + NAMESPACE_URI + "\">" +
                        "<as:task><as:id>1</as:id><as:description>TestTask</as:description><as:state>DONE</as:state></as:task>" +
                        "</as:createTaskRequest>");

        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault());

        requestPayload = new StringSource(
                "<as:getTaskRequest xmlns:as=\"" + NAMESPACE_URI + "\">" +
                        "<as:id>1</as:id>" +
                        "</as:getTaskRequest>");

        Source responsePayload = new StringSource(
                "<as:getTaskResponse xmlns:as=\"" + NAMESPACE_URI + "\">" +
                        "<as:task><as:id>1</as:id><as:description>TestTask</as:description><as:state>DONE</as:state></as:task>" +
                        "</as:getTaskResponse>");

        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload));
    }
}