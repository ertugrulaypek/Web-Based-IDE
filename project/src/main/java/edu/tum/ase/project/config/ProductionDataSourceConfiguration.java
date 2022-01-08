package edu.tum.ase.project.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class ProductionDataSourceConfiguration {

    @Value("${DB_JDBC_URL:}")
    private String dbJdbcUrl;

    @Value("${DB_HOST:localhost}")
    private String dbHost;

    @Value("${DB_PORT:5432}")
    private String dbPort;

    @Value("${DB_NAME:my_db}")
    private String dbName;

    @Value("${DB_USERNAME:admin}")
    private String dbUsername;

    @Value("${DB_PASSWORD:test}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        if(dbJdbcUrl.isEmpty()){
            hikariConfig.setUsername(dbUsername);
            hikariConfig.setPassword(dbPassword);
            hikariConfig.setJdbcUrl("jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName);
        }
        else
            hikariConfig.setJdbcUrl(dbJdbcUrl);

        return new HikariDataSource(hikariConfig);
    }

}
