package boot.backend.mapper;

import boot.backend.repository.TaskEntity;
import com.anderscore.soap.StateTo;
import com.anderscore.soap.TaskTo;
import org.springframework.stereotype.Component;

@Component
public class TaskToMapperImpl implements TaskToMapper {

    @Override
    public TaskTo asTaskTo(TaskEntity taskEntity) {
        TaskTo taskTo = new TaskTo();
        taskTo.setId(taskEntity.getId());
        taskTo.setDescription(taskEntity.getDescription());
        taskTo.setState(StateTo.fromValue(taskEntity.getState().name()));

        return taskTo;
    }

    @Override
    public TaskEntity asTaskEntity(TaskTo taskTo) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDescription(taskTo.getDescription());
        taskEntity.setState(TaskEntity.State.valueOf(taskTo.getState().name()));

        return taskEntity;
    }
}