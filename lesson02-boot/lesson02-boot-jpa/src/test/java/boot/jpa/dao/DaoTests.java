package boot.jpa.dao;

import boot.jpa.entity.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Add entity annotations, implement DAOs")
@SpringBootTest
@Transactional
public class DaoTests {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private TraineeDao traineeDao;

    @Autowired
    private TrainingDao trainingDao;

    @Test
    @Sql("/init-schema.sql")
    public void testDaos(){
        Course course = new Course();
        course.setTitle("Spring Power Workshop");
        course.setDuration(5);
        course.setLevel(Level.EXPERT);

        courseDao.create(course);

        Address address = new Address();
        address.setStreet("Frankenwerft");
        address.setNumber("35");
        address.setCountry(Country.GERMANY);

        addressDao.create(address);

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

        trainingDao.create(training);
        traineeDao.create(trainee);

        assertNotNull(course.getId());
        assertNotNull(address.getId());
        assertNotNull(trainee.getId());
        assertNotNull(training.getId());

        Course persistedCourse = courseDao.find(course.getId());
        Address persistedAddress = addressDao.find(address.getId());
        Trainee persistedTrainee = traineeDao.find(trainee.getId());
        Training persistedTraining = trainingDao.find(training.getId());
        List<Training> persistedTrainings = trainingDao.findAll();

        assertEquals(course, persistedCourse);
        assertEquals(address, persistedAddress);
        assertEquals(trainee, persistedTrainee);
        assertEquals(training, persistedTraining);
        assertEquals(Arrays.asList(training), persistedTrainings);

        assertEquals(address, persistedTrainee.getAddress());
        assertEquals(course, persistedTraining.getCourse());
        assertEquals(Arrays.asList(trainee), persistedTraining.getTrainees());
        assertEquals(Arrays.asList(training), persistedTrainee.getTrainings());
    }
}