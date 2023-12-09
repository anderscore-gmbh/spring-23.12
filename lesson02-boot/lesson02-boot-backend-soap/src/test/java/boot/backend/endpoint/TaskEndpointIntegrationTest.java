package boot.backend.endpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;

import static org.springframework.ws.test.server.RequestCreators.withPayload;

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
        // TODO: Requests versenden, Response prüfen
    }
}