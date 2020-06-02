package br.com.joacirjunior.corebanking;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {PostgresqlContainer.Initializer.class})
class CoreBankingApplicationTests {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getInstance();

}
