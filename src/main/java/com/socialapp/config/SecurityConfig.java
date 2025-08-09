package com.socialapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Main security configuration class for the application.
 * <p>
 * This class uses {@link EnableWebSecurity} to enable Spring Security's web security support
 * and provides a {@link DefaultSecurityFilterChain} bean to configure security settings like
 * CSRF protection, authorization rules, and session management.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the primary security filter chain for all HTTP requests.
     * <p>
     * This bean defines the security rules that protect the application's endpoints.
     * It specifies which paths are public and which require authentication.
     *
     * @param http The {@link HttpSecurity} object to configure, which is the main entry
     * point for customizing security settings.
     * @return The configured {@link DefaultSecurityFilterChain} instance.
     * @throws Exception if an error occurs during the configuration.
     */
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable Cross-Site Request Forgery (CSRF) protection
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Permit all requests to endpoints under "/api/test/".
                        .requestMatchers("/api/test/**").permitAll()
                        // Require authentication for any other request that is not explicitly matched above.
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
