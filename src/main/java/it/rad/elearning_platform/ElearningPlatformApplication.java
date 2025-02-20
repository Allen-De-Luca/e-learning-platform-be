package it.rad.elearning_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElearningPlatformApplication {

	public static void main(String[] args) {

		SpringApplication.run(ElearningPlatformApplication.class, args);
	}

}
