package com.make_your_choice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
class MakeYourChoiceApplicationTests {

	@BeforeAll
	static void initEnv() {
		String profile = System.getProperty("spring.profiles.active", "test");
		Dotenv dotenv = Dotenv.configure()
				.filename(".env." + profile)
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

	}

	@Test
	void contextLoads() {
	}
}
