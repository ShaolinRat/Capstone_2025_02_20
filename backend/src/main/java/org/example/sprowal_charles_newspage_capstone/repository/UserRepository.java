package org.example.sprowal_charles_newspage_capstone.repository;

import org.example.sprowal_charles_newspage_capstone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}