package com.make_your_choice.infrastructure.config.security.chains;

import com.make_your_choice.infrastructure.config.security.headers.SecurityHeadersConfig;
import com.make_your_choice.infrastructure.config.security.rules.AuthorizationRules;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final AuthorizationRules authorizationRules;
    private final SecurityHeadersConfig headersConfig;

    @Value("${FRONTEND_URL:http://localhost:*}")
    private String frontendUrl;

    public SecurityConfig(AuthorizationRules authorizationRules, SecurityHeadersConfig headersConfig) {
        this.authorizationRules = authorizationRules;
        this.headersConfig = headersConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> {
            var source = new UrlBasedCorsConfigurationSource();
            var config = new CorsConfiguration();
            config.setAllowedOriginPatterns(List.of(frontendUrl));
            config.setAllowedMethods(List.of("GET"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(List.of("*"));
            source.registerCorsConfiguration("/**", config);
            cors.configurationSource(source);
        }).csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        http.authorizeHttpRequests(auth -> {
            authorizationRules.publicEndpoints()
                    .forEach(ep -> auth.requestMatchers(ep.getMethod(), ep.getPath()).permitAll());

            authorizationRules.privateEndpoints()
                    .forEach(ep -> auth.requestMatchers(ep.getMethod(), ep.getPath()).authenticated());

            auth.anyRequest().denyAll();
        });

        http.httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        headersConfig.applyHeaders(http);

        return http.build();
    }
}
