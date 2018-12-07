package leo.dbproxy.starter;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "hikari")
public class DBProperties {
    private HikariDataSource master;
    private List<HikariDataSource> slaves;
}
