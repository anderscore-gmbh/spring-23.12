package boot.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

// tag::code[]
@SpringBootTest("spring.datasource.url=")
@AutoConfigureMockMvc
@Disabled("Implement TaskController first")
public class TaskControllerTest {
    // end::code[]

    private static final String TASK = "{\"description\":\"Learn using MockMvc\",\"dateDue\":\"2019-05-23T11:15:32Z\",\"state\":\"STARTED\"}";
    private static final String TASK_WITH_ID = TASK.replace("{", "{\"id\":1, ");

    private String expectedState = "STARTED";
    private long id;

    // tag::code[]
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEmptyRepository() throws Exception {
        mockMvc.perform(get("/tasks/")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("[]"));
    }
    // end::code[]

    @Test
    public void testCRUD() throws Exception {
        insert();
        findOne();
        findAll(1);
        findByState("STARTED", 1);
        findByState("DONE", 0);
        update();
        findOne();
        findByState("STARTED", 0);
        findByState("DONE", 1);
        deleteTask();
        findAll(0);
    }

    @Test
    public void testErrorHandling() throws Exception {
        // TODO: Datensaetze anlegen wg. Conflict (409) vs. NotFound (404)
        // TODO: Assertions in Test verschieben
        insertWithId();
        updateConlict();
        updateConlictWithoutId();
        deleteNotExisting();
        findNotExisting();
        updateNotExisting();
    }

    private void insert() throws Exception {
        MvcResult result = mockMvc.perform(post("/tasks").content(TASK).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, startsWith("http://localhost/tasks/")))
                .andExpect(jsonPath("$").doesNotExist()) // emtpy body
                .andReturn();
        String locationValue = (String) result.getResponse().getHeaderValue(HttpHeaders.LOCATION);
        id = Long.parseLong(locationValue.substring("http://localhost/tasks/".length()));
    }

    private void insertWithId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(post("/tasks").content(TASK_WITH_ID).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict());
        checkErrorContent(resultActions, 409, "Create");
    }

    private void findOne() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/tasks/" + id))
                .andExpect(status().isOk());
        checkResultContent(resultActions);
    }

    public void findAll(int expectedCount) throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id").value(hasSize(expectedCount)));
    }

    public void findByState(String state, int expectedCount) throws Exception {
        mockMvc.perform(get("/tasks?state=" + state))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id").value(hasSize(expectedCount)));
    }

    private void update() throws Exception {
        expectedState = "DONE";
        String task = TASK_WITH_ID.replace(":1,", ":" + id + ",").replace("STARTED", expectedState);
        mockMvc.perform(put("/tasks/" + id).content(task).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist()); // emtpy body;
    }

    private void updateConlict() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(put("/tasks/2").content(TASK_WITH_ID).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict());
        checkErrorContent(resultActions, 409, "Update");
    }

    private void updateConlictWithoutId() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(put("/tasks/2").content(TASK).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict());
        checkErrorContent(resultActions, 409, "Update");
    }

    public void deleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isNoContent());
    }

    public void deleteNotExisting() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(delete("/tasks/42"))
                .andExpect(status().isNotFound());
        checkErrorContent(resultActions, 404, "There is no");
    }

    private void findNotExisting() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/tasks/42"))
                .andExpect(status().isNotFound());
        checkErrorContent(resultActions, 404, "There is no");
    }

    private void updateNotExisting() throws Exception {
        String task = TASK_WITH_ID.replace(":1,", ":42,");
        ResultActions resultActions = mockMvc
                .perform(put("/tasks/42").content(task).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
        checkErrorContent(resultActions, 404, "There is no");
    }

    private void checkResultContent(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(jsonPath("description").value("Learn using MockMvc"))
                .andExpect(jsonPath("state").value(expectedState))
                .andExpect(jsonPath("dateDue").value(startsWith("2019-05-23T11:15:32")))
                .andExpect(jsonPath("id").value(id));
    }

    private void checkErrorContent(ResultActions resultActions, int code, String message) throws Exception {
        resultActions
                .andExpect(jsonPath("code").value(code))
                .andExpect(jsonPath("message").value(startsWith(message)));
    }
    // tag::code[]
}
// end::code[]
