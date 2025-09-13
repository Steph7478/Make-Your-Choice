package com.make_your_choice.infrastructure.config.security.chains;

import com.make_your_choice.infrastructure.config.security.headers.SecurityHeadersConfig;
import com.make_your_choice.infrastructure.config.security.rules.AuthorizationRules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    private final AuthorizationRules authorizationRules;
    private final SecurityHeadersConfig headersConfig;

    public SecurityConfig(AuthorizationRules authorizationRules, SecurityHeadersConfig headersConfig) {
        this.authorizationRules = authorizationRules;
        this.headersConfig = headersConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

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
