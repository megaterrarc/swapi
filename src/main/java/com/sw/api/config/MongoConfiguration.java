package com.sw.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.sw.api.data.repository")
public class MongoConfiguration {


}
