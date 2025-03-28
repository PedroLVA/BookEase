package com.bookease.bookease.repositories;
import com.bookease.bookease.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {

   UserDetails findByEmail(String email);
}
