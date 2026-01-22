package com.city.auth;

import com.city.auth.dto.AuthResponse;
import com.city.auth.dto.LoginRequest;
import com.city.auth.dto.RegisterRequest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

     @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @GetMapping("/public")
    public String publico() {
        return "Todos pueden ver esto";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public Map<String, Object> user(@AuthenticationPrincipal UserDetails user) {
        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getUsername());
        response.put("roles", user.getAuthorities());
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public String adminEndpoint() {
        return "Solo ADMIN";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/dashboard")
    public String dashboard() {
        return "ADMIN o USER";
    }

    @PreAuthorize("denyAll()")
    @GetMapping("/bloqueado")
    public String bloqueado() {
        return "Nunca accesible";
    }





}
