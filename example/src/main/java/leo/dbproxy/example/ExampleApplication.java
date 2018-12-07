package leo.dbproxy.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableJpaRepositories
@EntityScan("leo.dbproxy.example.domain")
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class);
    }

}
