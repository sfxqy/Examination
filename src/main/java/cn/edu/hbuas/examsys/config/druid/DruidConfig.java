package cn.edu.hbuas.examsys.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
//@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource druidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(30);
        dataSource.setMinIdle(3);
        dataSource.setInitialSize(3);
        try {
            dataSource.addFilters("stat");
            dataSource.addFilters("wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
