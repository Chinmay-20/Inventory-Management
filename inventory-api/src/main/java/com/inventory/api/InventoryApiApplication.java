package com.inventory.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Enables caching across the project
public class InventoryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApiApplication.class, args);
	}

}
