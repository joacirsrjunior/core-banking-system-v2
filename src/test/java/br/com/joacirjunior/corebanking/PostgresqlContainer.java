package br.com.joacirjunior.corebanking;

import org.junit.ClassRule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.spock.Testcontainers;

import static br.com.joacirjunior.corebanking.service.AccountServiceTest.postgreSQLContainer;

@TestConfiguration
@Testcontainers
public class PostgresqlContainer extends PostgreSQLContainer<PostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:10.6";
    private static PostgresqlContainer container;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getInstance();

    private PostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgresqlContainer getInstance() {
        if (container == null) {
            container = new PostgresqlContainer()
                    .withDatabaseName("core")
                    .withUsername("postgres")
                    .withPassword("admin");
        }
        return container;
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
    }

}
