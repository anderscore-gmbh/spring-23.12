package boot.fragments.jpa;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import org.junit.jupiter.api.Test;

import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskEntity.State;

/**
 * Verwendet die META-INF/persistence.xml
 */
public class PlainJpaTest {
    
    @Test
    public void testJpaQuery() {
        // tag::em[]
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Tasks"); // Name der Persistence Unit
        EntityManager em = factory.createEntityManager();
        Query query = em.createQuery("select t from TaskEntity t");
        List<TaskEntity> tasks = query.getResultList();
        tasks.forEach(System.out::println);
        // end::em[]
    }

    @Test
    public void testCriteriaApiQuery() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Tasks");
        EntityManager em = factory.createEntityManager();

        // tag::crit[]
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TaskEntity> q = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> from = q.from(TaskEntity.class);
        q.select(from);
        
        TypedQuery<TaskEntity> query = em.createQuery(q);
        List<TaskEntity> tasks = query.getResultList();
        tasks.forEach(System.out::println);
        // end::crit[]
    }

    @Test
    public void insertData() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Tasks");
        EntityManager em = factory.createEntityManager();
        TaskEntity task = new TaskEntity();
        task.setDateDue(Instant.now());
        task.setDescription("Learn JPA");
        task.setState(State.STARTED);

        em.getTransaction().begin();
        em.merge(task);
        em.getTransaction().commit();
    }
}
