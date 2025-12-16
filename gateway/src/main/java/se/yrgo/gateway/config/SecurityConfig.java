package se.yrgo.gateway.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String SECRET = "b5d0c2c5a0458ea8ce20400c141f7f3237c52e5d0ac560fe224dcf64d72a7bc6";

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] keyBytes = SECRET.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
