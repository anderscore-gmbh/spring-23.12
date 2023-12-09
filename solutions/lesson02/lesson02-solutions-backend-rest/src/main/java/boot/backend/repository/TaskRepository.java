package boot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// tag::def[]
public interface TaskRepository extends
        JpaRepository<TaskEntity, Long>, // Spring Data und JPQL
        JpaSpecificationExecutor<TaskEntity> // JPA criteria API
{

    List<TaskEntity> findTaskEntitiesByStateOrderByDateDueDesc(TaskEntity.State state); // <1>

    @Query("select t from TaskEntity t where t.state = :state order by t.dateDue desc") // <2>
    List<TaskEntity> findByJpaqlQuery(@Param("state") TaskEntity.State state);
}
// end::def[]
