package boot.fragments.jpa;

import java.util.List;
import java.util.Properties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import boot.backend.repository.TaskEntity;

@SpringJUnitConfig
public class SpringJpaTest {

    @Configuration
    @EnableTransactionManagement
    static class Config {

        // tag::emfactory[]
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource()); // <1>
            em.setPackagesToScan(new String[] { "boot.backend.repository" }); // <2>

            JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);
            em.setJpaProperties(additionalProperties());

            return em;
        }

        Properties additionalProperties() {
            Properties properties = new Properties();
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            properties.setProperty("hibernate.hbm2ddl.auto", "create"); // <3>
            return properties;
        }
        // end::emfactory[]

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
    }

    // tag::pc[]
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void test() {
        Query query = entityManager.createQuery("select t from TaskEntity t");
        List<TaskEntity> tasks = query.getResultList();
        tasks.forEach(System.out::println);
    }
    // end::pc[]

    // tag::em[]
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testUsingEntityManagerFactory() {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("select t from TaskEntity t");
        List<TaskEntity> tasks = query.getResultList();
        tasks.forEach(System.out::println);
    }
    // end::em[]
}
