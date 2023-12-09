package boot.frontend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import boot.frontend.model.Task;
import boot.frontend.model.Task.State;

public class TaskServiceTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

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
