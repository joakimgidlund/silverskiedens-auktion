package se.yrgo.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import se.yrgo.auth.entity.UserInfo;
import se.yrgo.auth.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    JwtService jwtService;

    private SecretKey getTestSignKey() {
        return Keys.hmacShaKeyFor(JwtService.SECRET.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void generateToken_userExists_generatesReadableJwt() {
        UserInfo user = new UserInfo();
        user.setId(42L);
        user.setUsername("alice");

        when(userRepository.findByUsername("alice"))
                .thenReturn(Optional.of(user));

        String token = jwtService.generateToken("alice");

        assertThat(token)
                .isNotNull()
                .isNotEmpty()
                .doesNotContain("No userId found");

        Claims claims = Jwts.parser()
                .verifyWith(getTestSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertThat(claims.getSubject()).isEqualTo("alice");
        assertThat(claims.getIssuer()).isEqualTo("auction-api");
        assertThat(claims.get("user-id", Long.class)).isEqualTo(42L);
        assertThat(claims.getExpiration()).isAfter(claims.getIssuedAt());
    }

    @Test
    void generateToken_userDoesNotExist_throwsException() {
        when(userRepository.findByUsername("missing"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> jwtService.generateToken("missing"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("missing");
    }
}