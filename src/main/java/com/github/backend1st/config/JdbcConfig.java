package com.github.backend1st.config;

import com.github.backend1st.properties.DataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
@RequiredArgsConstructor
public class JdbcConfig {
    private final DataSourceProperties dataSourceProperties;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("12341234");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/be_commerce_1?useUnicode=true&characterEncoding=UTF-8");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate1() { return new JdbcTemplate(dataSource()); }
    @Bean(name = "tm1")
    public PlatformTransactionManager transactionManager1() { return new DataSourceTransactionManager(dataSource()); }
}
