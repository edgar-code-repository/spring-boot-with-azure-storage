package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringBootWithAzureStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithAzureStorageApplication.class, args);
		log.debug("[main][connectionString: {}]", System.getenv("STORAGE_CONNECTION_STRING"));
	}

}
