package com.project.app;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
@EnableCaching
@Cacheable
@CachePut
@CacheEvict
public class ProjectECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectECommerceApplication.class, args);
	}

}
