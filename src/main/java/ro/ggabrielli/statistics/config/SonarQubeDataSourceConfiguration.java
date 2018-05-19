package ro.ggabrielli.statistics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(basePackages = {"ro.ggabrielli.statistics.repository.jpa.sonarqube"},
                        entityManagerFactoryRef = "sonarQubeEntityManager",
                        transactionManagerRef =  "sonarQubeTransactionManager")
public class SonarQubeDataSourceConfiguration {

    private final Environment environment;

    public SonarQubeDataSourceConfiguration(@Autowired Environment environment){
        this.environment = environment;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean sonarQubeEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(sonarQubeDataSource());
        em.setPackagesToScan("ro.ggabrielli.statistics.domain.sonarqube");

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                environment.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public DataSource sonarQubeDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getProperty("sonar.jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager sonarQubeTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sonarQubeEntityManager().getObject());
        return transactionManager;
    }
}
