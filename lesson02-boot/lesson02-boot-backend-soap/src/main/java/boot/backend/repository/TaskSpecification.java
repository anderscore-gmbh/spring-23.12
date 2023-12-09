package boot.backend.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification implements Specification<TaskEntity> {

    private final TaskSearchCriteria criteria;
    
    public TaskSpecification(TaskSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<TaskEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        query.orderBy(builder.desc(root.get("dateDue")));
        return builder.equal(root.get("state"), criteria.getState());
    }
}