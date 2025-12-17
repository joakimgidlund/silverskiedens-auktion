package se.yrgo.bidbroker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth -> oauth
                        .bearerTokenResolver(cookieBearerTokenResolver())
                        .jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public BearerTokenResolver cookieBearerTokenResolver() {
        return request -> {
            if (request.getCookies() == null) {
                return null;
            }

            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
            return null;
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String secret = "b5d0c2c5a0458ea8ce20400c141f7f3237c52e5d0ac560fe224dcf64d72a7bc6";
        return NimbusJwtDecoder.withSecretKey(
                new javax.crypto.spec.SecretKeySpec(
                        secret.getBytes(),
                        "HmacSHA256"))
                .build();
    }
}
