package se.yrgo.auth.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));

            String token = jwtService.generateToken(authRequest.getUsername());

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);

            return new ResponseEntity<>("Login successful.", HttpStatus.OK);

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>("Unauthorized.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/test")
    public String getMethodName() {
        return "Test success";
    }

}
