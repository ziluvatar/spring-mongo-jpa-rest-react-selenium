package com.eduardods.companies.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Profile("mongo")
@EnableMongoRepositories(basePackages = {"com.eduardods.companies.db.mongo"})
public class MongoConfiguration {
}
