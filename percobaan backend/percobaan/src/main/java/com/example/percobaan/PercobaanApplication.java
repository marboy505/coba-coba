package com.example.percobaan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.percobaan.model")
public class PercobaanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PercobaanApplication.class, args);
	}
}
