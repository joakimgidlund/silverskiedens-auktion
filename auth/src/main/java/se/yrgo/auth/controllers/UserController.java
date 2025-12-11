package se.yrgo.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.auth.entity.AuthRequest;
import se.yrgo.auth.entity.UserInfo;
import se.yrgo.auth.service.JwtService;
import se.yrgo.auth.service.UserInfoService;

@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(authRequest.getUsername()), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);

    }
}
