package com.lavkesh.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.lavkesh.repository"})
@EntityScan(basePackages = {"com.lavkesh.entity"})
@EnableTransactionManagement
public class DatabaseConfig {}
