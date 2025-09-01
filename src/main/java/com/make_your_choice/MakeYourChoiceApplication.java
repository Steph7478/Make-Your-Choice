package com.make_your_choice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MakeYourChoiceApplication {

	public static void main(String[] args) {
		// mvn spring-boot:run
		// -Dspring-boot.run.arguments="--spring.profiles.active=dev"
		String profile = System.getProperty("spring.profiles.active", "dev");
		Dotenv dotenv = Dotenv.configure()
				.filename(".env." + profile)
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		System.out.println("Database URL: " + dotenv.get("SPRING_DATASOURCE_URL"));

		SpringApplication.run(MakeYourChoiceApplication.class, args);
	}
}
