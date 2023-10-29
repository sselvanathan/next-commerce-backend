package com.sselvanathan.nextcommercebackend.auth;

import com.sselvanathan.nextcommercebackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        // Validate the user's credentials or token
        // If valid, return a success response with user information
        // If invalid, return an error response
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/authorize")
    public ResponseEntity<User> authorize (
            @RequestBody AuthenticationRequest request
    ) {
        // Validate the user's credentials or token
        // If valid, return a success response with user information
        // If invalid, return an error response
        return ResponseEntity.ok(service.authorize(request));
    }
}
