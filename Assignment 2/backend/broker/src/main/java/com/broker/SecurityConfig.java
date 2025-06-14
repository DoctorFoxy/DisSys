package com.broker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/api/items").permitAll()

                                .requestMatchers("/api/suppliers/**").permitAll() // TESTING, REMOVE LATER!!!!!

                                .requestMatchers("/api/authdebug/public").permitAll()
                                .requestMatchers("/api/authdebug/privatescoped").hasAuthority("SCOPE_read:orders")

                                .requestMatchers(HttpMethod.GET, "/api/orders").hasAuthority("SCOPE_read:orders")
                                .requestMatchers(HttpMethod.POST, "/api/orders").authenticated()
                                .requestMatchers("/api/orders/user").authenticated()

                                .requestMatchers("/api/orders/status/*").permitAll() // TODO: maybe change for more protection

                                .requestMatchers("/api/orders/*").hasAuthority("SCOPE_read:orders")

                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/**").permitAll()
                )
                .cors(Customizer.withDefaults()) // For CORS config see BrokerBackendApplication
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }
}