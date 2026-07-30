package com.fernando84.employeeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fernando84.employeeapi.DTO.AuthResponse;
import com.fernando84.employeeapi.DTO.LoginRequest;
import com.fernando84.employeeapi.DTO.RegisterRequest;
import com.fernando84.employeeapi.model.AppUser;
import com.fernando84.employeeapi.repository.AppUserRepository;
import com.fernando84.employeeapi.service.JwtService;
import com.fernando84.employeeapi.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password())); // never saves plain text
        user.setRole(request.role());
        appUserRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        return new AuthResponse(jwtService.generateToken(userDetails));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        // throws exception if user does not exists or password is invalid
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        /*
         * UserDetails userDetails = org.springframework.security.core.userdetails.User
         * .withUsername(request.username())
         * .password("") // token does not carries a password
         * .roles("USER") // placeholder
         * .build();
         */

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
