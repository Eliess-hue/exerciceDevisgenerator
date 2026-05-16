package com.eliess.devis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliess.devis.dto.AuthResponse;
import com.eliess.devis.dto.LoginRequest;
import com.eliess.devis.dto.RegisterRequest;
import com.eliess.devis.security.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok().build();

    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);

    }

}
