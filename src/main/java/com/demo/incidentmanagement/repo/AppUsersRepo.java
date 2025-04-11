package com.demo.incidentmanagement.repo;

import com.demo.incidentmanagement.model.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for DB operations on AppUsers table
 */
public interface AppUsersRepo extends JpaRepository<AppUsers, Long> {
    /**
     * Find User info by email
     * @param email User's email
     * @return AppUsers object
     */
    Optional<AppUsers> findByEmail(String email);
}
