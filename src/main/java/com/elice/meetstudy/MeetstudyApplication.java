package com.elice.meetstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeetstudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetstudyApplication.class, args);
	}

}
