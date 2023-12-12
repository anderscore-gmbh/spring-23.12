package integration;

import boot.jpa.dao.CourseDao;
import boot.jpa.dao.CourseDaoImpl;
import boot.jpa.dao.TraineeDao;
import boot.jpa.entity.*;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@ExtendWith(SpringExtension.class)
@Sql("/init-schema.sql")
public class CourseDaoTestsNoBoot {
    @Autowired
    private CourseDao courseDao;

    @Autowired
    TraineeDao traineeDao;

    @Test
    public void testFind(){
        int num = courseDao.findAll().size();
        assertEquals(0,num);
    }
    @Test
    public void testCreate(){
        Course course = new Course();
        course.setTitle("Spring Power Workshop");
        course.setDuration(5);
        course.setLevel(Level.EXPERT);

        courseDao.create(course);
        assertNotNull(course.getId());
    }
    @Test
    public void testFind2(){
        int num = courseDao.findAll().size();
        assertEquals(0,num);
    }
    @Configuration
    @Import(JpaConfig.class)
    public static class Config {
        @Bean
        public CourseDao courseDao(){
            return new CourseDaoImpl();
        }
        @MockBean
        public TraineeDao traineeDao;


    }
}
