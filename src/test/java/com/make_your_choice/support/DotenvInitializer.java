package com.make_your_choice.support;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;

public class DotenvInitializer {
    @BeforeAll
    public static void loadEnv() {
        Dotenv.configure()
                .filename(".env.test")
                .load();
    }
}
