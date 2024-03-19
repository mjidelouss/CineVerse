package com.cine.verse.repository;

import com.cine.verse.domain.AppUser;
import com.cine.verse.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    long countByRole(Role role);
}
