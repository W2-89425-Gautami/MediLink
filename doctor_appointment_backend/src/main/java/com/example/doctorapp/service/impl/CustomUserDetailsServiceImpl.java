package com.example.doctorapp.service.impl;

import com.example.doctorapp.entity.User;
import com.example.doctorapp.enums.Role;
import com.example.doctorapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by email (assuming username is email)
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        
        // Return custom UserDetails implementation
        return new CustomUserPrincipal(user);
    }
    
    // Inner class implementing UserDetails
    private static class CustomUserPrincipal implements UserDetails {
        private final User user;
        
        public CustomUserPrincipal(User user) {
            this.user = user;
        }
        
        @Override
        public String getUsername() {
            return user.getEmail();
        }
        
        @Override
        public String getPassword() {
            return user.getPassword();
        }
        
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            // Convert user role enum to GrantedAuthority
            Role role = user.getRole();
            if (role == null) {
                role = Role.USER; // Default role
            }
            // Use the enum name directly (it should already be ROLE_USER, ROLE_DOCTOR, etc.)
            return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        }
        
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
        
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }
        
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
        
        @Override
        public boolean isEnabled() {
            // Use the enabled field from User entity
            return user.getEnabled() != null ? user.getEnabled() : true;
        }
        
        // Getter to access the original User entity if needed
        public User getUser() {
            return user;
        }
    }
}