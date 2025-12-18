package se.yrgo.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import se.yrgo.auth.repository.UserRepository;

@Component
public class JwtService {
    private final UserRepository repo;
    public static final String SECRET = "b5d0c2c5a0458ea8ce20400c141f7f3237c52e5d0ac560fe224dcf64d72a7bc6"; // NOT SAFE

    public JwtService(UserRepository repo) {
        this.repo = repo;
    }

    private SecretKey getSignKey() {
        // Directly use bytes of the secret
        byte[] keyBytes = SECRET.getBytes(); // or Base64.getDecoder().decode(SECRET) if you encode
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        var user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .subject(username)
                .issuer("auction-api")
                .issuedAt(now)
                .expiration(expiry)
                .claim("user-id", user.getId())
                .signWith(getSignKey(), SIG.HS256)
                .compact();
    }
}
