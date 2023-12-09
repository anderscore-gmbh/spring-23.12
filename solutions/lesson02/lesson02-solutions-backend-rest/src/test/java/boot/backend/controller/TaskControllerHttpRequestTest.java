package boot.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import boot.backend.repository.TaskEntity;

// tag::code[]
@SpringBootTest(properties = "spring.datasource.url=", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerHttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGet() throws Exception {
        TaskEntity task = restTemplate.getForObject("http://localhost:" + port + "/tasks/42", TaskEntity.class);
    }
    // end::code[]

    @Test
    public void testInsertDelete() throws Exception {
        String url = "http://localhost:" + port + "/tasks";
        TaskEntity task = new TaskEntity();
        task.setDescription("Study REST principles");
        task.setDateDue(Instant.now());
        task.setState(TaskEntity.State.OPEN);
        URI uri = restTemplate.postForLocation(url, task);
        System.out.println(uri);

        ResponseEntity<List<TaskEntity>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<TaskEntity>>() {
                });
        List<TaskEntity> tasks = response.getBody();
        System.out.println(tasks);
        assertThat(tasks).hasSize(1);

        restTemplate.delete(url + tasks.get(0).getId());
    }

    // tag::code[]
}
// end::code[]
