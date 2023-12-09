package boot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {

    List<TaskEntity> findTaskEntitiesByStateOrderByDateDueDesc(TaskEntity.State state);

    @Query("select t from TaskEntity t where t.state = :state order by t.dateDue desc")
    List<TaskEntity> findByJpaqlQuery(@Param("state") TaskEntity.State state);
}