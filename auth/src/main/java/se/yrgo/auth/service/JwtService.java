package se.yrgo.auth.service;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import se.yrgo.auth.entity.UserInfo;
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
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000 * 60 * 60); // 1 hour
        var user = repo.findByUsername(username);
        Long userId;

        if(user.isPresent()) {
            userId = user.get().getId();
        return Jwts.builder()
                .subject(username)
                .issuer("auction-api")
                .issuedAt(now)
                .expiration(expiry)
                .claim("user-id", userId)
                .signWith(getSignKey(), SIG.HS256)
                .compact();
        }
        return "No userId found";
    }
}
