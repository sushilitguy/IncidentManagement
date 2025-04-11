package com.demo.incidentmanagement.repo;

import com.demo.incidentmanagement.model.AppUsersData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for DB operations on AppUserData table
 */
public interface AppUsersDataRepo extends JpaRepository<AppUsersData, Long> {
}
