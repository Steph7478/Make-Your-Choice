package com.make_your_choice.infrastructure.config.security.rules;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorizationRules {

    public static class EndpointRule {
        private final HttpMethod method;
        private final String path;
        private final boolean requiresAuth;

        public EndpointRule(HttpMethod method, String path, boolean requiresAuth) {
            this.method = method;
            this.path = path;
            this.requiresAuth = requiresAuth;
        }

        public HttpMethod getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }

        public boolean requiresAuth() {
            return requiresAuth;
        }
    }

    public List<EndpointRule> publicEndpoints() {
        return List.of(
                new EndpointRule(HttpMethod.GET, "/choices/**", false),
                new EndpointRule(HttpMethod.GET, "/dialog/**", false));
    }

    public List<EndpointRule> privateEndpoints() {
        return List.of(
        // new EndpointRule(HttpMethod.POST, "/choices/**", true),
        // new EndpointRule(HttpMethod.POST, "/dialog/**", true)
        );
    }
}
