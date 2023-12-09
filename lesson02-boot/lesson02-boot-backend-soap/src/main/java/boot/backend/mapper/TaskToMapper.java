package boot.backend.mapper;

import boot.backend.repository.TaskEntity;
import com.anderscore.soap.TaskTo;

public interface TaskToMapper {

    TaskTo asTaskTo(TaskEntity taskEntity);

    TaskEntity asTaskEntity(TaskTo taskTo);
}