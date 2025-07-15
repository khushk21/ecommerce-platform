package com.example.auth_service.service;

import com.example.auth_service.dto.AuthRequest;
import com.example.auth_service.dto.AuthResponse;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.model.Role;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.RoleRepository;
import com.example.auth_service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;          // <–– import this
import java.util.Set;                  // <–– import this

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder pwEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authManager,
                       UserRepository userRepo,
                       RoleRepository roleRepo,
                       PasswordEncoder pwEncoder,
                       JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.pwEncoder = pwEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.username()) || userRepo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Username or email already taken");
        }
        Role customerRole = roleRepo.findById("ROLE_CUSTOMER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        // Create user via setters (no Lombok builder)
        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPassword(pwEncoder.encode(req.password()));
        user.setRoles(Collections.singleton(customerRole));  // a singleton set

        userRepo.save(user);
    }

    public AuthResponse login(AuthRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );
        String token = jwtUtil.generateToken(req.username());
        return new AuthResponse(token, token);
    }
}
