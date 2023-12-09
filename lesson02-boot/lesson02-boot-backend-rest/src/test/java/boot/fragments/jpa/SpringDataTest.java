package boot.fragments.jpa;

import java.util.List;
import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskEntity.State;
import boot.backend.repository.TaskRepository;
import boot.backend.repository.TaskSearchCriteria;
import boot.backend.repository.TaskSpecification;

/**
 * Benötigt laufenden HSQLDB-Server! 
 */
@SpringJUnitConfig
public class SpringDataTest {

    // tag::config[]
    @Configuration
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackageClasses = TaskRepository.class)
    // end::config[]
    static class Config {

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean em
                    = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource());
            em.setPackagesToScan(new String[]{"boot.backend.repository"});

            em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            em.setJpaProperties(additionalProperties());

            return em;
        }

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
            dataSource.setUrl("jdbc:hsqldb:mem:mymemdb");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            return dataSource;
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(emf);
            return transactionManager;
        }

        @Bean
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
            return new PersistenceExceptionTranslationPostProcessor();
        }

        Properties additionalProperties() {
            Properties properties = new Properties();
            properties.setProperty("hibernate.hbm2ddl.auto", "create");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            return properties;
        }
    }

    // tag::query[]
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFindAll() {
        List<TaskEntity> tasks = taskRepository.findAll();
        tasks.forEach(System.out::println);
    }
    // end::query[]

    @Test
    public void testFindTaskEntitiesByStateOrderByDateDueDesc() {
        List<TaskEntity> tasks = taskRepository.findTaskEntitiesByStateOrderByDateDueDesc(State.STARTED);
        tasks.forEach(System.out::println);
    }

    @Test
    public void testFindByJpaqlQuery() {
        List<TaskEntity> tasks = taskRepository.findByJpaqlQuery(State.STARTED);
        tasks.forEach(System.out::println);
    }

    @Test
    public void testFindCriteriaApi() {
        // tag::crit[]
        TaskSpecification spec = new TaskSpecification(new TaskSearchCriteria(State.STARTED));
        List<TaskEntity> tasks = taskRepository.findAll(spec);
        tasks.forEach(System.out::println);
        // end::crit[]
    }
}
