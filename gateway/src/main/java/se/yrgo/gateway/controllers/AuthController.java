package se.yrgo.gateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.gateway.dto.AuthRequestDTO;
import se.yrgo.gateway.dto.UserInfoDTO;
import se.yrgo.gateway.service.AuthServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> gatewayRegister(@RequestBody UserInfoDTO userInfo) {
        String resp = authService.registerUser(userInfo);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> gatewayLogin(@RequestBody AuthRequestDTO authRequest) {
        String resp = authService.loginUser(authRequest);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
