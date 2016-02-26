package com.eduardods.companies.main;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("jpa")
@EnableJpaRepositories(basePackages = {"com.eduardods.companies.db"})
@EntityScan(basePackages = {"com.eduardods.companies.db"})
public class JPAConfiguration {
}
