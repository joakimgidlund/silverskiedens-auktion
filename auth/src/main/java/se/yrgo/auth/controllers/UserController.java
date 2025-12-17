package se.yrgo.auth.controllers;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import se.yrgo.auth.entity.AuthRequest;
import se.yrgo.auth.entity.UserInfo;
import se.yrgo.auth.service.JwtService;
import se.yrgo.auth.service.UserInfoService;

@RestController
@RequestMapping("/auth")
public class UserController {
    private UserInfoService userInfoService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    public UserController(UserInfoService userInfoService, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userInfoService = userInfoService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        return new ResponseEntity<>(userInfoService.addUser(userInfo), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));

            String token = jwtService.generateToken(authRequest.getUsername());

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .sameSite("Lax")
                        .maxAge(Duration.ofHours(24))
                        .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(Map.of("token", token));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized"));
        }
    }

    @GetMapping("/test")
    public String getMethodName() {
        return "Test success";
    }

}
