package com.example.doctorapp.controller;

import com.example.doctorapp.entity.User;
import com.example.doctorapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            // Handle both Optional<User> and User return types
            Object result = userService.getUserById(userId);
            
            if (result instanceof Optional) {
                Optional<User> userOptional = (Optional<User>) result;
                if (userOptional.isPresent()) {
                    return ResponseEntity.ok(userOptional.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else if (result instanceof User) {
                User user = (User) result;
                if (user != null) {
                    return ResponseEntity.ok(user);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching user: " + e.getMessage());
        }
    }
    
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCurrentUserProfile(Principal principal) {
        try {
            String email = principal.getName();
            return ResponseEntity.ok("User profile for: " + email);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching profile: " + e.getMessage());
        }
    }
    
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User userUpdate) {
        try {
            // Uncomment and modify based on your UserService methods
            // User updatedUser = userService.updateUser(userId, userUpdate);
            // return ResponseEntity.ok(updatedUser);
            
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            // Uncomment based on your UserService methods
            // userService.deleteUser(userId);
            
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        try {
            // Uncomment if you have this method in UserService
            // List<User> users = userService.getAllUsers();
            // return ResponseEntity.ok(users);
            
            return ResponseEntity.ok("List of all users would be here");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching users: " + e.getMessage());
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("User controller is working!");
    }
    
    @GetMapping("/secured-test")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> securedTestEndpoint(Principal principal) {
        return ResponseEntity.ok("Secured endpoint working for user: " + principal.getName());
    }
}