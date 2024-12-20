package com.leets.X.domain.user.repository;

import com.leets.X.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    boolean existsByCustomId(String customId);

    Optional<User> findByEmail(String email);

    Optional<User> findByCustomId(String customId);

}
