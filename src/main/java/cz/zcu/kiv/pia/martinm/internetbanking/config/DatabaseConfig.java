package cz.zcu.kiv.pia.martinm.internetbanking.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configures data layer of application. Configuration class contains configuration of
 * data source and persistence.
 *
 * Date: 18.12.2018
 *
 * @author Martin Matas
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("cz.zcu.kiv.pia.martinm.internetbanking.dao")
@PropertySource("classpath:db.properties")
public class DatabaseConfig {

    /**
     * Configures entityManager with configuration stored in db.properties on classpath. EntityManager is configured to
     * scan domain package of application that contains entities of data model.
     *
     * @param dataSource - configured instance of dataSource
     * @param env - environment that provides access to configuration file
     * @return configured entityManagerFactory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("cz.zcu.kiv.pia.martinm.internetbanking.domain");

        Properties properties = new Properties();
        // Hibernate-specific configuration
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.validator.apply_to_ddl", env.getProperty("hibernate.validator.apply_to_ddl"));
        // Hibernate-C3P0 configuration
        properties.setProperty("connection.provider_class", env.getProperty("connection.provider_class"));
        properties.setProperty("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
        properties.setProperty("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
        properties.setProperty("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
        properties.setProperty("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
        properties.setProperty("hibernate.c3p0.idle_test_period", env.getProperty("hibernate.c3p0.idle_test_period"));

        emf.setJpaProperties(properties);
        return emf;
    }

    /**
     * Defines primary data source that will be used. Data source is configured via configuration stored
     * in db.properties on classpath. Primary annotation is used due to multiple existing
     * configurations of data source.
     *
     * @param env - environment that provides access to configuration file
     * @return configured data source
     */
    @Primary
    @Bean
    public DataSource dataSource(Environment env) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
        dataSource.setUrl(env.getProperty("database.url"));
        dataSource.setUsername(env.getProperty("database.username"));
        dataSource.setPassword(env.getProperty("database.password"));
        return dataSource;
    }

    /**
     * Creates new {@link JpaTransactionManager} instance from given entityManageFactory.
     *
     * @param entityManagerFactory - configured instance of entityManageFactory
     * @return instance of PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

}
