package com.anderscore.justparkit.abrechnung.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    private static final String[] PACKAGES_TO_SCAN = {"com.anderscore.justparkit.abrechnung.persistence"};

    @Bean
    public DataSource dataSource(Environment env) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
        hikariConfig.setUsername(env.getProperty("jdbc.username"));
        hikariConfig.setPassword(env.getProperty("jdbc.password"));
        hikariConfig.setDriverClassName(env.getProperty("jdbc.driverClassName"));

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPackagesToScan(PACKAGES_TO_SCAN);
        emf.setJpaProperties(jpaProperties(env));

        return emf;
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);

        return vendorAdapter;
    }

    private Properties jpaProperties(Environment env) {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }
}