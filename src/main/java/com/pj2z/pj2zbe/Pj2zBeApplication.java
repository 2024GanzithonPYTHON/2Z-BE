package com.pj2z.pj2zbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Pj2zBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pj2zBeApplication.class, args);
	}

}
