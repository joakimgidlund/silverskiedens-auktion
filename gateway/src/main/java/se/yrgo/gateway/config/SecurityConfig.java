package se.yrgo.gateway.config;

import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String SECRET = "b5d0c2c5a0458ea8ce20400c141f7f3237c52e5d0ac560fe224dcf64d72a7bc6";

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .bearerTokenResolver(bearerTokenResolver())
                        .jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] keyBytes = SECRET.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Frontend origins
        config.setAllowedOrigins(List.of("http://localhost:8085"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return request -> {
            String path = request.getRequestURI();
            if (path.startsWith("/api/auth/")) {
                return null; // skip JWT auth
            }

            // Otherwise, get JWT from cookie
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("jwt".equals(cookie.getName())) {
                        System.out.println("Cookie found: " + cookie);
                        return cookie.getValue();
                    }
                }
            }
            return null;
        };
    }
}
