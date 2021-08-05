package com.sw.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;


@Configuration
@EnableTransactionManagement
@EnableRedisRepositories(repositoryImplementationPostfix = "Redis")
public class RedisConfiguration {



}
