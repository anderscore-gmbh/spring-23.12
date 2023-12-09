package boot.backend.mapper;

import boot.backend.repository.TaskEntity;

import com.anderscore.soap.TaskTo;
import org.springframework.stereotype.Component;

@Component
public class TaskToMapperImpl implements TaskToMapper {

    @Override
    public TaskTo asTaskTo(TaskEntity taskEntity) {
        // TODO: Implementieren

        throw new UnsupportedOperationException();
    }

    @Override
    public TaskEntity asTaskEntity(TaskTo taskTo) {
        // TODO: Implementieren

        throw new UnsupportedOperationException();
    }
}