package com.make_your_choice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MakeYourChoiceApplication {

	public static void main(String[] args) {
		String profile = System.getProperty("spring.profiles.active", "dev");
		Dotenv dotenv = Dotenv.configure()
				.filename(".env." + profile)
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(MakeYourChoiceApplication.class, args);
	}
}
