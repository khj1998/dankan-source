package com.dankan.repository;

import com.dankan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByNickname(String name);
    public Optional<User> findByEmail(String email);
}
