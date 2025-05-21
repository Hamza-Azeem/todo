package com.todo.todolist.configuration;

import com.todo.todolist.repository.*;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    DataSource driverManagerDatasource(){
        return new DriverManagerDataSource();
    }

    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    Jdbi jdbi(DataSource dataSource){
        return Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin())
                .installPlugin(new PostgresPlugin());
    }

    @Bean
    public UserRepository userRepository(Jdbi jdbi) {
        return jdbi.onDemand(UserRepository.class);
    }

    @Bean
    public TaskRepository taskRepository(Jdbi jdbi) {
        return jdbi.onDemand(TaskRepository.class);
    }

    @Bean
    public TaskStatusRepository taskStatusRepository(Jdbi jdbi) {
        return jdbi.onDemand(TaskStatusRepository.class);
    }

    @Bean
    public UserTokenRepository userTokenRepository(Jdbi jdbi) {
        return jdbi.onDemand(UserTokenRepository.class);
    }

    @Bean
    public PermissionRequestRepository permissionRequestRepository(Jdbi jdbi) {
        return jdbi.onDemand(PermissionRequestRepository.class);
    }
}
