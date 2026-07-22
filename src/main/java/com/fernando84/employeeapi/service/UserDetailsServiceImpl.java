package com.fernando84.employeeapi.service;

import com.fernando84.employeeapi.repository.AppUserRepository;
import com.fernando84.employeeapi.model.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword()) // pre-hashed
                .roles(appUser.getRole().name()) // Spring adds the prefix "ROLE_" internally
                .build();
    }
}
