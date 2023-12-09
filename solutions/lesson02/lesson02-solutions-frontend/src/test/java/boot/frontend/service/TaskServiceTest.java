package boot.frontend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import boot.frontend.model.Task;
import boot.frontend.model.Task.State;

@SpringJUnitConfig(TaskServiceConfig.class)
@TestPropertySource(properties = "backend.url=http://mockhost:7777")
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testFindAll() throws ParseException {
        Task task1 = createTask(1, "Aufgabe 1", "2019-05-27T15:34:18Z", State.OPEN);
        Task task2 = createTask(2, "Aufgabe 2", "2019-05-20T11:18:09Z", State.STARTED);

        mockServer.expect(ExpectedCount.once(), requestTo("http://mockhost:7777/tasks"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("[" + asJson(task1) + "," + asJson(task2) + "]"));

        List<Task> tasks = taskService.findAll();
        assertThat(tasks).hasSize(2).contains(task1, task2);
        mockServer.verify();
    }

    @Test
    public void testFindById() throws ParseException, URISyntaxException {
        Task task = createTask(27, "Aufgabe 27", "2019-05-27T15:34:18Z", State.OPEN);

        mockServer.expect(ExpectedCount.once(), requestTo("http://mockhost:7777/tasks/27"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(asJson(task)));

        Task response = taskService.findById(27);
        assertThat(response).isEqualTo(task);
        mockServer.verify();
    }

    @Test
    public void testCreateTask() throws ParseException, URISyntaxException {
        Task task = createTask(0, "Aufgabe 12", "2019-05-27T15:34:18Z", State.OPEN);

        mockServer.expect(ExpectedCount.once(), requestTo("http://mockhost:7777/tasks"))
                .andExpect(method(HttpMethod.POST)).andExpect(jsonPath("description", equalTo("Aufgabe 12")))
                .andExpect(jsonPath("dateDue", equalTo("2019-05-27T15:34:18Z")))
                .andExpect(jsonPath("state", equalTo("OPEN")))
                .andRespond(withStatus(HttpStatus.OK).location(new URI("http://mockhost:7777/tasks/12")));

        taskService.create(task);
        mockServer.verify();
    }

    @Test
    public void testUpdateTask() throws ParseException, URISyntaxException {
        Task task = createTask(29, "Aufgabe 29", "2019-05-27T15:34:18Z", State.STARTED);

        mockServer.expect(ExpectedCount.once(), requestTo("http://mockhost:7777/tasks/29"))
                .andExpect(method(HttpMethod.PUT)).andExpect(jsonPath("description", equalTo("Aufgabe 29")))
                .andExpect(jsonPath("dateDue", equalTo("2019-05-27T15:34:18Z")))
                .andExpect(jsonPath("state", equalTo("STARTED"))).andRespond(withStatus(HttpStatus.OK));

        taskService.update(task);
        mockServer.verify();
    }

    @Test
    public void testDeleteTask() throws ParseException, URISyntaxException {
        mockServer.expect(ExpectedCount.once(), requestTo("http://mockhost:7777/tasks/42"))
                .andExpect(method(HttpMethod.DELETE)).andRespond(withStatus(HttpStatus.NO_CONTENT));

        taskService.delete(42);
        mockServer.verify();
    }

    private Task createTask(long id, String description, String dateDue, State state) throws ParseException {
        Task task = new Task();
        task.setId(id);
        task.setDescription(description);
        task.setDateDue(sdf.parse(dateDue));
        task.setState(state);
        return task;
    }

    private String asJson(Task task) {
        return "{\"id\":" + task.getId() + "," + "\"description\":\"" + task.getDescription() + "\"," + "\"dateDue\":\""
                + sdf.format(task.getDateDue()) + "\"," + "\"state\":\"" + task.getState() + "\"}";
    }
}
