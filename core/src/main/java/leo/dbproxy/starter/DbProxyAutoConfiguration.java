package leo.dbproxy.starter;

import leo.dbproxy.starter.aop.DataSourceAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConditionalOnMissingBean(DbProxyAutoConfiguration.class)
@EnableConfigurationProperties(DBProperties.class)
@Import(DataSourceAspect.class)
public class DbProxyAutoConfiguration {

    @Autowired
    private DBProperties dbProperties;


    @Bean
    @ConditionalOnMissingBean
    public DelegatingDataSource delegatingDataSource() {
        return new DelegatingDataSource(dbProperties);
    }


    @Bean
    @Primary
    public PlatformTransactionManager txManager(@Qualifier("delegatingDataSource") DelegatingDataSource delegatingDataSource) {
        return new DataSourceTransactionManager(delegatingDataSource);
    }

}

