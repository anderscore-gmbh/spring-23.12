package boot.jpa.repository;

import boot.jpa.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RepositoryTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Test
    @Sql("/init-schema.sql")
    public void testRepositories(){
        Course course = new Course();
        course.setTitle("Spring Power Workshop");
        course.setDuration(5);
        course.setLevel(Level.EXPERT);

        courseRepository.save(course);

        Address address = new Address();
        address.setStreet("Frankenwerft");
        address.setNumber("35");
        address.setCountry(Country.GERMANY);

        addressRepository.save(address);

        Training training = new Training();
        training.setStartDate(new GregorianCalendar(2020, 4, 20).getTime());
        training.setStartDate(new GregorianCalendar(2020, 4, 24).getTime());
        training.setCourse(course);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Arno");
        trainee.setLastName("Nühm");
        trainee.setAddress(address);

        training.setTrainees(Arrays.asList(trainee));
        trainee.setTrainings((Arrays.asList(training)));

        trainingRepository.save(training);
        traineeRepository.save(trainee);

        assertNotNull(course.getId());
        assertNotNull(address.getId());
        assertNotNull(trainee.getId());
        assertNotNull(training.getId());

        Optional<Course> persistedCourse = courseRepository.findById(course.getId());
        Optional<Address> persistedAddress = addressRepository.findById(address.getId());
        Optional<Trainee> persistedTrainee = traineeRepository.findById(trainee.getId());
        Optional<Training> persistedTraining = trainingRepository.findById(training.getId());

        assertTrue(persistedCourse.isPresent());
        assertTrue(persistedAddress.isPresent());
        assertTrue(persistedTrainee.isPresent());
        assertTrue(persistedTraining.isPresent());

        assertEquals(course, persistedCourse.get());
        assertEquals(address, persistedAddress.get());
        assertEquals(trainee, persistedTrainee.get());
        assertEquals(training, persistedTraining.get());

        assertEquals(address, persistedTrainee.get().getAddress());
        assertEquals(course, persistedTraining.get().getCourse());
        assertEquals(Arrays.asList(trainee), persistedTraining.get().getTrainees());
        assertEquals(Arrays.asList(training), persistedTrainee.get().getTrainings());
    }

    @Test
    @Sql({"/init-schema.sql", "/init-data.sql"})
    public void testFinder(){
        // TODO: Load all courses having titles containing "Datenbanken"
        List<Course> courses = courseRepository.findByTitleLike("%Datenbanken%");
        assertEquals(2, courses.size());

        // TODO: Load courses having a duration between 3 and 5 in ascending duration order with page size 2
        Pageable request = PageRequest.of(0, 2, Sort.by("duration").ascending());
        Page<Course> page = courseRepository.findByDurationBetween(3, 5, request);
        assertEquals(2, page.getContent().size());
        assertEquals(3, page.getTotalElements());
    }
}