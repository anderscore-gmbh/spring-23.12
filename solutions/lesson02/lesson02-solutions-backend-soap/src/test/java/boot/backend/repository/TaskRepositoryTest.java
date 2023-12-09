package boot.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.function.Function;

import jakarta.transaction.Transactional;

import org.assertj.core.data.TemporalOffset;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import boot.backend.repository.TaskEntity.State;

// tag::Fragen[]
// Frage 1: Was bewirkt das "spring.datasource.url="?
@SpringBootTest(properties = "spring.datasource.url=")
// Frage 2: Wozu ist @Transactional notwendig? 
@Transactional
// end::Fragen[]
public class TaskRepositoryTest {
    private static final TemporalOffset<Temporal> OFFSET = new TemporalUnitLessThanOffset(1, ChronoUnit.SECONDS);

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void init() {
        taskRepository.deleteAll();
        assertThat(findAll()).isEmpty();
    }

    private TaskEntity create(String description, TaskEntity.State state, int dueInDays) {
        TaskEntity task = new TaskEntity();
        task.setDescription(description);
        task.setState(state);
        task.setDateDue(Instant.now().plus(dueInDays, ChronoUnit.DAYS));
        return task;
    }

    /**
     * Schritt 1: Einen Datensatz speichern und prüfen, ob genau ein Datensatz
     * gespeichert wurde. Implementieren Sie dafür die Methoden 'create' und
     * 'findAll'.
     */
    @Test
    public void testCreate() {
        TaskEntity task = create("Spring-Data lernen", State.STARTED, 1);
        TaskEntity created = create(task);
        List<TaskEntity> tasks = findAll();

        assertThat(created).isEqualToIgnoringGivenFields(task, "id", "dateDue");
        assertThat(created.getId()).isPositive();
        assertThat(created.getDateDue()).isCloseTo(task.getDateDue(), OFFSET);
        assertThat(tasks).hasSize(1).contains(created);
        assertContentEquals(tasks.get(0), created);
    }

    private void assertContentEquals(TaskEntity task1, TaskEntity task2) {
        // tag::Frage3[]
        // Frage 3: Warum ist kein exakter Vergleich bei 'dateDue' möglich?
        assertThat(task1).isEqualToIgnoringGivenFields(task2, "dateDue");
        assertThat(task1.getDateDue()).isCloseTo(task2.getDateDue(), OFFSET);
        // end::Frage3[]
    }

    /**
     * Speichert einen Task-Datensatz in der Datenbank. Macht keine Sonderbehandlung
     * für den Fall, dass es das entsprechende Task bereits gibt.
     * 
     * @param task das zu speichernde Objekte
     * @return das gespeicherte Objekt
     */
    private TaskEntity create(TaskEntity task) {
        // TODO: Implementieren Sie diese Methode.
        TaskEntity created = taskRepository.save(task);
        return created;
    }

    /**
     * Liefert die List aller Tasks in der Datenbank.
     * 
     * @return Liste aller Tasks
     */
    private List<TaskEntity> findAll() {
        // TODO: Implementieren Sie diese Methode.
        return taskRepository.findAll();
    }

    /**
     * Schritt 2: Create, Read, Update und Delete testen. Implementieren Sie dafür
     * die Methoden 'read', 'update' und 'delete'.
     */
    @Test
    public void testCrud() {
        TaskEntity task = create("Spring Security verstehen", State.OPEN, 2);
        TaskEntity created = create(task);
        Long id = created.getId();
        TaskEntity read = read(id);
        TaskEntity copy = create("Sprint Container verstehen", State.DONE, -2);
        copy.setId(id);
        TaskEntity updated = update(copy);
        TaskEntity updatedRead = read(id);
        delete(id);
        TaskEntity deletedRead = read(id);

        assertContentEquals(read, created);
        assertContentEquals(updated, copy);
        assertContentEquals(updatedRead, copy);
        assertThat(deletedRead).isNull();
        assertThat(findAll()).isEmpty();
    }

    /**
     * Ein Task aus der Datenbank auslesen.
     * 
     * @param id die Id des Tasks
     * @return das entsprechenden Task oder null, wenn keines vorhanden ist
     */
    private TaskEntity read(long id) {
        // TODO: Implementieren Sie diese Methode.
        TaskEntity task = taskRepository.findById(id).orElse(null);
        return task;
    }

    /**
     * Ein Task aktualisieren. Die Method speichert das übergebene Task und ersetzt
     * gegebenfalls das vorherige. Falls kein entsprechendes Task mit der angegeben
     * id vorhanden ist, wird einer neuer Datensatz erstellt.
     * 
     * @param task das zu speichernde Task
     * @return das aktualisierte Task
     */
    private TaskEntity update(TaskEntity task) {
        // TODO: Implementieren Sie diese Methode.
        TaskEntity updated = taskRepository.save(task);
        return updated;
    }

    /**
     * Löscht einen Task Datensatz.
     * 
     * @param id id des zu löschenden Datensatzes
     */
    private void delete(long id) {
        // TODO: Implementieren Sie diese Methode.
        taskRepository.deleteById(id);
    }

    @Test
    public void testQueryByName() {
        checkQuery(taskRepository::findTaskEntitiesByStateOrderByDateDueDesc);
    }

    @Test
    public void testJpaqlQuery() {
        checkQuery(taskRepository::findByJpaqlQuery);
    }

    private void checkQuery(Function<TaskEntity.State, List<TaskEntity>> query) {
        createTasks();
        List<TaskEntity> tasks = query.apply(State.OPEN);
        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getDescription()).isEqualTo("Task 3");
        assertThat(tasks.get(1).getDescription()).isEqualTo("Task 2");
    }

    private List<TaskEntity> createTasks() {
        return List.of(create(create("Task 1", TaskEntity.State.STARTED, 1)),
                create(create("Task 2", TaskEntity.State.OPEN, 1)), create(create("Task 3", TaskEntity.State.OPEN, 2)));
    }

    /**
     * Schritt 3: Abfrage mit JPA Criteria API. Implementieren Sie dafür die Methode
     * 'findTaskEntitiesByStateOrderByDateDueDescUsingCriteriaApi'.
     */
    @Test
    public void testCriteriaQuery() {
        checkQuery(this::findTaskEntitiesByStateOrderByDateDueDescUsingCriteriaApi);
    }

    /**
     * Liefert die Liste aller Tasks mit dem entsprechenden Status. Die Datensätze
     * sind absteigend nach Fälligkeitsdatum sortiert. Die Abfrage wird mit der JPA
     * Criteria API durchführt unter Verwendung von {@link TaskSearchCriteria}.
     * 
     * @param state der Status der gesuchten Tasks
     * @return List der entsprechenden Task Datensätze
     */
    private List<TaskEntity> findTaskEntitiesByStateOrderByDateDueDescUsingCriteriaApi(State state) {
        // TODO: Implementieren Sie diese Methode.
        TaskSpecification spec = new TaskSpecification(new TaskSearchCriteria(state));
        return taskRepository.findAll(spec);
    }

    /**
     * Schritt 4: Was passiert, wenn Sie diesen Test enabeln? Wie erklären Sie sich
     * das Verhalten?
     */
    @Disabled
    @Test
    public void testImplicitUpdate() {
        List<TaskEntity> tasks = createTasks();
        tasks.get(0).setId(tasks.get(1).getId());
        findAll();
    }
}
