package com.example.doctorapp.repository;

import com.example.doctorapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);  // ✅ Correct
    Optional<User> findByUserName(String name);
	boolean existsByEmail(String email);
}
