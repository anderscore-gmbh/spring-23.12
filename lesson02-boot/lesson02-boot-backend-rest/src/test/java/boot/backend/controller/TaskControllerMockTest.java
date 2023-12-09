package boot.backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskRepository;

@SpringJUnitConfig
@WebMvcTest
@Disabled("Implement TaskController first")
public class TaskControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void testOneEntry() throws Exception {
        TaskEntity task = new TaskEntity();
        task.setId(42L);
        task.setDescription("Learn Mockito");
        task.setState(TaskEntity.State.OPEN);
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        String expected = "[{\"id\":42,\"description\":\"Learn Mockito\",\"dateDue\":null,\"state\":\"OPEN\"}]";
        mockMvc.perform(get("/tasks/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void testInternalError() throws Exception {
        when(taskRepository.findById(42L)).thenThrow(new NullPointerException("mock"));

        mockMvc.perform(get("/tasks/42"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"code\":500,\"message\":\"mock\"}"));
    }

}
