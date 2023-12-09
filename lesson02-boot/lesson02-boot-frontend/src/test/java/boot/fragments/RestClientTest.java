package boot.fragments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import boot.frontend.model.Task;
import boot.frontend.model.Task.State;

public class RestClientTest {

    private RestTemplate restTemplate;

    @BeforeEach
    void init() {
        restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("user", "secret"));
    }

    @Test
    void test() throws UnsupportedEncodingException {
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString("user:secret".getBytes("UTF-8"));

        // tag::code[]
        var mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(), requestTo("http://fakehost/tasks")).andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("state", equalTo("OPEN")))
                .andExpect(content().string(containsString("JSONPath lernen")))
                .andExpect(header(HttpHeaders.AUTHORIZATION, equalTo(basicAuth)))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body("{\"error\":{\"code\":123, \"message\":\"Simulierter Fehler\"}"));
        // end::code[]

        var ex = assertThrows(HttpClientErrorException.class, this::sendPostRequest);
        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ex.getResponseBodyAsString().contains("Simulierter Fehler");

        mockServer.verify();
    }

    private URI sendPostRequest() {
        Task task = new Task();
        task.setState(State.OPEN);
        task.setDescription("JSONPath lernen");

        URI uri = restTemplate.postForLocation("http://fakehost/tasks", task);
        return uri;
    }
}
