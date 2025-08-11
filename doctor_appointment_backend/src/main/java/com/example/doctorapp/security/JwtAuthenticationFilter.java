package com.example.doctorapp.security;

import org.springframework.beans.factory.annotation.Autowired;
//import com.example.doctorapp.service.impl.CustomUserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.doctorapp.service.impl.CustomUserDetailsServiceImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;
    
    public JwtAuthenticationFilter(JwtTokenHelper jwtTokenHelper,
            CustomUserDetailsServiceImpl userDetailsService) {
    		this.jwtTokenHelper = jwtTokenHelper;
    		this.userDetailsService = userDetailsService;
}

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Extract Authorization Header
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2. Get Token from Header
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // Remove "Bearer " prefix
            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (Exception ex) {
                System.out.println("JWT extraction failed: " + ex.getMessage());
            }
        }

        // 3. Validate Token and Authenticate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 4. Continue Filter Chain
        filterChain.doFilter(request, response);
    }
}
